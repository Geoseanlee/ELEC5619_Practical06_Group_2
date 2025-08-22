# ELEC5619_Practical06_Group_2
 # Inventory & Order Management

## 1. Overview
The project aims to provide businesses with comprehensive inventory and order management capabilities, ensuring efficient execution from product receipt, inventory level monitoring, supplier replenishment orders, to customer order fulfillment. The goal is to reduce out-of-stock situations, optimize inventory costs, and facilitate users to quickly find and access inventory information, perform outbound and inbound operations, and improve the accuracy and speed of order fulfillment.

## 2. High‑level goals
This project's goal is to build a centralized, real-time inventory and order management system. It will enable companies to efficiently manage inventory levels across multiple warehouses, quickly place orders with suppliers, and ensure rapid and accurate fulfillment of customer orders. Through this system, companies can reduce out-of-stock risks, optimize inventory costs, and enhance customer satisfaction.

## 3. Tech Stack
- Frontend: Next.js + React.js + Tailwind CSS
- Backend: Spring Boot (Java)
- Database: MySQL
- Version Control: GitHub
- Project Management: Jira (Agile/Scrum)

## 4. Team Roles
| Name         | Role Assignment                               | Responsibilities                                                                 |
|-------------|-----------------------------------------------|----------------------------------------------------------------------------------|
| George Li   | Product Owner (PO); Front-end Development      | Project management; front-end development; front-end/back-end integration        |
| Xinyu Gu    | Scrum Master; Front-end Development            | Scrum management on the Jira board; assist with front-end development            |
| Jingze Kong | Back-end Development                           | Functional development; database integration                                     |
| Tianchi Ma  | Back-end Development                           | Functional development; database integration                                     |
| Huaicheng Wu| Back-end Development                           | Functional development; database integration                                     |

## 5. Key Features

### 5.1 Front-end & User Experience

#### 5.1.1 Responsive Layout
- The same page automatically adapts to desktop, tablet, and mobile.
- Mobile keeps the same whitespace and visual hierarchy as web, so information is easy to scan.

#### 5.1.2 Usability & Aesthetics
- Clear, readable UI with descriptive labels and text.
- Adequate color contrast to improve legibility.
- Buttons provide immediate feedback to prevent repeated clicks/taps.
- Overall interactions are convenient and smooth.

### 5.2 Back-end

#### 5.2.1 Inventory Management
- Real-time inventory quantity monitoring
- Safety stock threshold settings and alerts
- Multi-warehouse inventory synchronization

#### 5.2.2 Supplier Order Management
- Generate purchase orders (based on inventory and historical data)
- Supplier information management (contact person, supplier name)
- Order tracking (ordering, shipment, arrival, warehousing)

#### 5.2.3 Orders & Fulfillment
- Support end-to-end management of the order lifecycle
- Interact with the inventory domain: reserve/rollback inventory when creating/canceling orders
- Delivery tracking (generate picking list, delivery note, logistics order number)

