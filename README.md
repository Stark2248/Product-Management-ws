# **Product Management Service**

---

A service that handles all the Rest API calls on products and warehouse data.

---

### Tech Stack
1. Java
2. Spring-Boot
3. Spring-Data-Jpa
4. H2-InMemory-Database
5. Swagger 3

---

### Rest API Calls

> **Get Request="/products/all"**

Request to get all the product details from the table.

![get All Request](./pics/productgetAll.png)


> ** Get Request="/products/{id}" **

Request to get a specific product detail by Id from the table.

![get Id Request](./pics/productgetId.png)

> ** Post Request="/products/product" **

Request to create product in the table.

![post product Request](./pics/productpostSave.png)

> ** Put Request="/users/{id}" **

Request to update product in the table.

![put product request](./pics/productputId.png)

> **Delete Request="/users/{id}" **

Request to delete a specific product in the table.

![delete product request](./pics/productdeleteId.png)

> **Get Request="/warehouse/stocks" **

Request to get all warehouse details in the table.

![get All request](./pics/warehousegetStock.png)

> **Get Request="/warehouse/{id}" **

Request to get product stock deatils in different warehouse location details.

![get based on product id request](./pics/warehousegetId.png)

> **Get Request="/warehouse/{location}/{id}" **

Request to get a product details  warehouse details in the table.

![get product in a location request](./pics/warehousegetIdandLocation.png)

> **Get Request="/warehouse/locations/{location}" **

Request to get all warehouse details in a specific location in the table.

![get All in a location request](./pics/warehousegetByLocation.png)

> **Get Request="/warehouse/stock/{location}/{Id}" **

Request to get quantity of a product in a warehouse location in the table.

![get All request](./pics/warehousegetqty.png)

> **Post Request="/warehouse/stock" **

Request to save one warehouse detail in the table.

![post save request](./pics/warehousepoststock.png)

> **Put Request="/warehouse/{location}/{id}" **

Request to update a warehouse detail in the table.

![put update request](./pics/warehouseputIdnLoc.png)

> **Delete Request="/warehouse/{location]/{id}" **

Request to delete a warehouse detail in the table.

![delete request](./pics/warehousedeletebyIdLoc.png)


