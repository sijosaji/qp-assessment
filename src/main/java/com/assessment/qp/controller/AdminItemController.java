package com.assessment.qp.controller;

import com.assessment.qp.dto.ItemCreateRequest;
import com.assessment.qp.dto.ItemSearchDto;
import com.assessment.qp.dto.ItemUpdateRequest;
import com.assessment.qp.service.AdminItemService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("admin/grocery-item")
public class AdminItemController {
    @Autowired
    private AdminItemService adminItemService;
    @PostMapping
    /**
     * Resource Method to add Grocery Item to System.
     */
    public ResponseEntity addItem(@NotNull @RequestBody ItemCreateRequest createRequest) {
        return adminItemService.addItem(createRequest);
    }

    @GetMapping("{itemId}")
    /**
     * Resource method to view details of a single grocery Item.
     */
    public ResponseEntity getItem(@PathVariable("itemId") UUID itemId) {
        return adminItemService.getItem(itemId);
    }

    /**
     * Resource method to update details of a particular grocery item
     * @param itemId Id of the grocery item to be updated.
     * @param updateRequest Request body containing fields that needs to be updated.
     * @return
     */
    @PutMapping("{itemId}")
    public ResponseEntity updateItem(@PathVariable("itemId") UUID itemId,
            @NotNull @RequestBody ItemUpdateRequest updateRequest) {
        return adminItemService.updateItem(itemId, updateRequest);
    }

    /**
     * Resource method to delete a grocery Item.
     * @param itemId Id of the grocery item to be deleted.
     * @return Response indicating status of Item to be deleted.
     */
    @DeleteMapping("{itemId}")
    public ResponseEntity deleteItem(@PathVariable("itemId") UUID itemId) {
        return adminItemService.deleteItem(itemId);
    }

    /**
     * Resource method to search grocery items.
     * @param dto Dto containing filters and pagination params needed for searching.
     * @return List of Items that matches search criteria.
     */
    @PostMapping("search")
    public ResponseEntity searchItems(@RequestBody ItemSearchDto dto) {
        return adminItemService.searchItems(dto);
    }

    /**
     * Resource method to manage inventory of an Item.
     * @param itemId Id of the Item whose inventory details needs to be updated.
     * @param operator Operator to be applied on current Item quantity.
     * @param value The value by which value is to be incremented or decremented.
     * @return The final Inventory details of Item.
     */
    @PutMapping("{itemId}/manage-inventory")
    public ResponseEntity manageInventory(@PathVariable("itemId")UUID itemId,
            @RequestParam("operator") String operator, @Min(1)@RequestParam("value") Integer value) {
        return adminItemService.manageInventory(itemId, operator, value);
    }
}
