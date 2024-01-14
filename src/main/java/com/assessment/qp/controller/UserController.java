package com.assessment.qp.controller;

import com.assessment.qp.dto.BookingRequestDto;
import com.assessment.qp.dto.ItemSearchDto;
import com.assessment.qp.service.UserItemService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")

public class UserController {
    @Autowired
    private UserItemService userItemService;

    @PostMapping("grocery-item/search")
    /**
     * Resource method to search Items based on filters provided.
     * @param dto Dto that contains filters and pagination params to search and list data.
     * @return List of grocery Items that match the filters provided.
     */
    public ResponseEntity searchItems(@RequestBody ItemSearchDto dto) {
        return userItemService.searchItems(dto);
    }

    @PostMapping("{userId}/grocery-item/book-items")
    /**
     * Resource method to book grocery items selected by the user.
     * @param bookingRequestDto Request to book Grocery items.
     * @param userId Id of the User for which booking is to be made.
     * @return Response of the booking details made by the user.
     */
    public ResponseEntity bookItems(@PathVariable UUID userId,
            @RequestBody @NotNull List<BookingRequestDto> bookingRequestDto) {
        return userItemService.bookItems(bookingRequestDto, userId);
    }
}
