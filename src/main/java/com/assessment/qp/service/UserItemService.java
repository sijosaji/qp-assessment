package com.assessment.qp.service;

import com.assessment.qp.dao.BookingRepository;
import com.assessment.qp.dao.ItemRepository;
import com.assessment.qp.dto.BookingRequestDto;
import com.assessment.qp.dto.BookingResponseDto;
import com.assessment.qp.dto.ItemSearchDto;
import com.assessment.qp.dto.UserItemResponse;
import com.assessment.qp.entity.Booking;
import com.assessment.qp.entity.BookingDetail;
import com.assessment.qp.entity.Item;
import com.assessment.qp.helper.SpecificationHelper;
import com.assessment.qp.model.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Method to search Items based on filters provided.
     * @param dto Dto that contains filters and pagination params to search and list data.
     * @return List of grocery Items that match the filters provided.
     */
    public ResponseEntity searchItems(ItemSearchDto dto) {
        SpecificationHelper specificationHelper = new SpecificationHelper();
        List<String> unsupportedFields = convertAndModifyFilters(dto.getSearchCriteriaList());
        if (!unsupportedFields.isEmpty()) {
            return ResponseEntity.status(400).body("Following are unsupported fields im filter "+ unsupportedFields);
        }
        specificationHelper.setFilters(dto.getSearchCriteriaList());
        Specification<Item> itemSpecification = specificationHelper.buildSpecification();
        Pageable pageable = PageRequest.of(dto.getOffset(), dto.getLimit());
        Page<UserItemResponse> page = mapItemToUserResponse(itemRepository.findAll(itemSpecification, pageable));
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(page.toList());
    }

    private List<String> convertAndModifyFilters(List<SearchCriteria> searchCriteriaList) {
        List<String> unsupportedFields = new ArrayList<>();
        searchCriteriaList.forEach(searchCriteria -> {
            if (searchCriteria.getFieldName().equalsIgnoreCase("price")) {
                searchCriteria.setFieldName("sellingPrice");
            } else if (searchCriteria.getFieldName().equalsIgnoreCase("costPrice")) {
                unsupportedFields.add("costPrice");
            }
        });
        return unsupportedFields;
    }

    private PageImpl mapItemToUserResponse(Page<Item> paginatedItems) {
        List<Item> items = paginatedItems.toList();
        List<UserItemResponse> itemResponseList = new ArrayList<>();
        items.forEach(item -> itemResponseList.add(UserItemResponse.builder().name(item.getName()).id(item.getId()).
                    description(item.getDescription()).price(item.getSellingPrice()).quantity(item.getQuantity())
                    .expirationDate(item.getExpirationDate()).build()));
        return new PageImpl(itemResponseList);
    }

    /**
     * Method to book grocery items selected by the user.
     * @param bookingRequestDto Request to book Grocery items.
     * @param userId Id of the User for which booking is to be made.
     * @return Response of the booking details made by the user.
     */
    public ResponseEntity bookItems(List<BookingRequestDto> bookingRequestDto, UUID userId) {
        List<UUID> itemIds = bookingRequestDto.stream().map(BookingRequestDto::getItemId).toList();
        List<Item> itemsFromDb = itemRepository.findAllById(itemIds);
        Map<UUID, Item> itemById = itemsFromDb.stream().collect(Collectors.toMap(Item::getId, Function.identity()));
        List<UUID> errorIds = new ArrayList<>();
        if (itemsFromDb.size() == itemIds.size()) {
           bookingRequestDto.forEach(request -> {
               Item item = itemById.get(request.getItemId());
               if (item.getQuantity() < request.getQuantity()) {
                  errorIds.add(item.getId());
               } else {
                  item.setQuantity(item.getQuantity() - request.getQuantity());
               }
           });
           if (!errorIds.isEmpty()) {
               return ResponseEntity.status(400).body("Following Ids have less quantity than requested"+ errorIds);
           }
           itemRepository.saveAll(itemsFromDb);
           return saveBookingDetailsOfUser(bookingRequestDto, itemById, userId);
        } else {
            return ResponseEntity.status(400).body("Some Ids given are not present in the system");
        }
    }

    private ResponseEntity<BookingResponseDto> saveBookingDetailsOfUser(List<BookingRequestDto> bookingRequestDto,
            Map<UUID, Item> itemsFromDb, UUID userId) {
        UUID bookingId = UUID.randomUUID();
        Booking booking = Booking.builder().bookingId(bookingId).userId(userId).bookingTime(LocalDateTime.now()).build();
        List<BookingDetail> bookingDetails = bookingRequestDto.stream().map(request ->
            BookingDetail.builder().bookingId(bookingId).itemId(request.getItemId()).quantity(request.getQuantity())
                    .price(itemsFromDb.get(request.getItemId()).getSellingPrice()).id(UUID.randomUUID()).build()).toList();
        booking.setBookingDetailList(bookingDetails);
        bookingRepository.save(booking);
        List<UserItemResponse> userItemResponses = bookingDetails.stream().map(bookingDetail -> {
            BigDecimal totalAmount = bookingDetail.getPrice().multiply(BigDecimal.valueOf(bookingDetail.getQuantity()));
            return UserItemResponse.builder().id(bookingDetail.getItemId()).name(itemsFromDb.get(bookingDetail.getItemId()).getName())
                    .price(bookingDetail.getPrice()).quantity(bookingDetail.getQuantity()).totalAmount(totalAmount).build();
        }).toList();
        BigDecimal total = userItemResponses.stream().map(UserItemResponse::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BookingResponseDto bookingResponseDto = BookingResponseDto.builder().bookingId(bookingId).userId(userId)
                .bookedItems(userItemResponses).total(total).build();
        return ResponseEntity.status(200).body(bookingResponseDto);
    }
}
