# README

## API Endpoints

1. **/admin/grocery-item/**: POST endpoint to add Grocery Item to System.
    ```json
    {
        "name": "Parle-G",
        "description": "Glucose Biscuits",
        "quantity" : 50,
        "costPrice" : 10,
        "sellingPrice" : 20,
        "ëxpirationDate" : "2024-12-25"
    }
    ```


2. **/admin/grocery-item/{itemId}**: GET endpoint to fetch Grocery Item details based on given Item Id.

