package com.assessment.qp.service;

import com.assessment.qp.dao.ItemRepository;
import com.assessment.qp.dto.ItemCreateRequest;
import com.assessment.qp.dto.ItemSearchDto;
import com.assessment.qp.dto.ItemUpdateRequest;
import com.assessment.qp.entity.Item;
import com.assessment.qp.enums.InventoryOperation;
import com.assessment.qp.helper.SpecificationHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.assessment.qp.enums.InventoryOperation.MINUS;

@Service
public class AdminItemService {
    @Autowired
    private ItemRepository dao;
    ModelMapper MAPPER = new ModelMapper();

    /**
     * Resource Method to add Grocery Item to System.
     * @param createRequest Request containing Item details to be added.
     * @return Final state of Items that is stored.
     */
    public ResponseEntity<Item> addItem(ItemCreateRequest createRequest) {
        Item item = mapRequestToEntity(createRequest);
        item.setId(UUID.randomUUID());
        dao.save(item);
        return ResponseEntity.status(201).body(item);
    }

    private Item mapRequestToEntity(Object request) {
        return MAPPER.map(request, Item.class);
    }

    /**
     * Resource method to view details of a single grocery Item.
     * @param itemId Id of the item.
     * @return Details of a single grocery Item.
     */
    public ResponseEntity getItem(UUID itemId) {
        Optional<Item> item  = dao.findById(itemId);
        if (item.isPresent()) {
            return  ResponseEntity.status(200).body(item);
        }
        return ResponseEntity.status(404).body("Requested Grocery Item Not Found");
    }

    /**
     * Method to update details of a particular grocery item
     * @param itemId Id of the grocery item to be updated.
     * @param updateRequest Request body containing fields that needs to be updated.
     * @return
     */
    public ResponseEntity updateItem(UUID itemId, ItemUpdateRequest updateRequest) {
        Optional<Item> item = dao.findById(itemId);
        if (item.isPresent()) {
            mapUpdateRequestToItem(updateRequest, item.get());
            dao.save(item.get());
            return dao.findById(itemId).map(itemFromDb -> ResponseEntity.status(200).body(itemFromDb)).get();
        }
        return ResponseEntity.status(404).body("Item to be updated not found");
    }
    private void mapUpdateRequestToItem(ItemUpdateRequest updateRequest, Item item) {
        if (Objects.nonNull(updateRequest.getDescription())) {
            item.setDescription(updateRequest.getDescription());
        }
        if (Objects.nonNull(updateRequest.getName())) {
            item.setName(updateRequest.getName());
        }
        if (Objects.nonNull(updateRequest.getQuantity())) {
            item.setQuantity(updateRequest.getQuantity());
        }
        if (Objects.nonNull(updateRequest.getExpirationDate())) {
            item.setExpirationDate(updateRequest.getExpirationDate());
        }
        if (Objects.nonNull(updateRequest.getCostPrice())) {
            item.setCostPrice(updateRequest.getCostPrice());
        }
        if (Objects.nonNull(updateRequest.getSellingPrice())) {
            item.setSellingPrice(updateRequest.getSellingPrice());
        }
    }

    /**
     * Method to delete a grocery Item.
     * @param itemId Id of the grocery item to be deleted.
     * @return Response indicating status of Item to be deleted.
     */
    public ResponseEntity deleteItem(UUID itemId) {
        if (dao.existsById(itemId)) {
            dao.deleteById(itemId);
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).body("Item to be deleted is not found");
        }
    }

    /**
     * Method to search grocery items.
     * @param dto Dto containing filters and pagination params needed for searching.
     * @return List of Items that matches search criteria.
     */
    public ResponseEntity searchItems(ItemSearchDto dto) {
        SpecificationHelper specificationHelper = new SpecificationHelper();
        specificationHelper.setFilters(dto.getSearchCriteriaList());
        Specification<Item> itemSpecification = specificationHelper.buildSpecification();
        Pageable pageable = PageRequest.of(dto.getOffset(), dto.getLimit());
        Page<Item> page = dao.findAll(itemSpecification, pageable);
        return ResponseEntity.status(200).body(page);
    }

    /**
     * Method to manage inventory of an Item.
     * @param itemId Id of the Item whose inventory details needs to be updated.
     * @param operator Operator to be applied on current Item quantity.
     * @param value The value by which value is to be incremented or decremented.
     * @return The final Inventory details of Item.
     */
    public ResponseEntity manageInventory(UUID itemId, String operator, Integer value) {
        Optional<Item> item = dao.findById(itemId);
        InventoryOperation operation = InventoryOperation.getOperator(operator);
        if (item.isPresent()) {
            Integer itemQuantity = item.get().getQuantity();
            if (operation == MINUS && itemQuantity < value) {
                return ResponseEntity.status(400).body("Quantity of the item is less than value to be decremented");
            } else {
                item.get().setQuantity(operation == MINUS ? itemQuantity - value : itemQuantity + value);
                dao.save(item.get());
                return  ResponseEntity.status(200).body(item.get());
            }
        } else {
            return ResponseEntity.status(404).body("Item whose quantity is to be managed is not found");
        }
    }
}
