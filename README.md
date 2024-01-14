# README

## API Endpoints - Admin

1. **POST /admin/grocery-item/**: Endpoint to add Grocery Item to System.
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


2. **GET /admin/grocery-item/{itemId}**: Endpoint to fetch Grocery Item details based on given Item Id.
3. **PUT /admin/grocery-item/{itemId}**: Endpoint to update Grocery Item details based on given Item Id.
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
4. **DELETE /admin/grocery-item/{itemId}**: Endpoint to delete Grocery Item details from the system.
5. **POST /admin/grocery-item/search**: Endpoint to search and paginate grocery items based on given filters.
    
     ```json
    {
        "searchCriteriaList": [
             {
                 "fieldName" : "name",
                 "fieldValue" : "Parle-G",
                 "operator" : "eq"
             },
             {
                 "fieldName" : "costPrice",
                 "fieldValue" : 40.5,
                 "operator" : "gte"
             }
         ],
         "offset": 0,
         "limit": 10
     }
    ```
6. **PUT /admin/grocery-item/{itemId}/manage-inventory**: Endpoint to manage inventory quantity of a given item. Query params include **operator** ie (ADD/MINUS) and **value**.

## API Endpoints - User
1. **POST /user/grocery-item/search**: Endpoint to search and paginate grocery items based on given filters.
    
     ```json
    {
        "searchCriteriaList": [
             {
                 "fieldName" : "name",
                 "fieldValue" : "Parle-G",
                 "operator" : "eq"
             },
             {
                 "fieldName" : "costPrice",
                 "fieldValue" : 40.5,
                 "operator" : "gte"
             }
         ],
         "offset": 0,
         "limit": 10
     }
    ```
2. **POST /user/grocery-item/{userId}/book-items**: Endpoint to book grocery items with required quantity and returns booking itemId with Item details and total amount incurred.

    ```json
    [
        {
            "itemId": "b62e3bc5-0832-4c2b-860a-b92a36750ed1",
            "quantity": 10
        },
        {
            "itemId": "d6064a89-024b-4001-bb3e-b5edb573765e",
            "quantity": 5
        }
    ]
    ```
 
