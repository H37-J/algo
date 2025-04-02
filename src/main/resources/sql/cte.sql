CREATE TABLE Employees
(
    EmployeeID INT PRIMARY KEY,
    Name       NVARCHAR(100) NOT NULL,
    Position   NVARCHAR(100),
    ManagerID  INT           NULL,
    Department NVARCHAR(50),
    Salary     DECIMAL(10, 2),
    HireDate   DATE,
    FOREIGN KEY (ManagerID) REFERENCES Employees (EmployeeID)
);

-- 최상위 관리자 (CEO)
INSERT INTO Employees
VALUES (1, '김철수', 'CEO', NULL, '경영진', 100000, '2010-01-15');

-- 1레벨 관리자 (부서장)
INSERT INTO Employees
VALUES (2, '이영희', 'CTO', 1, '기술부', 80000, '2012-03-20'),
       (3, '박민수', 'CFO', 1, '재무부', 75000, '2011-11-05'),
       (4, '정수민', 'CMO', 1, '마케팅부', 70000, '2013-07-12');

-- 2레벨 직원
INSERT INTO Employees
VALUES (5, '최윤아', '개발팀장', 2, '기술부', 65000, '2015-02-18'),
       (6, '김태현', '재무팀장', 3, '재무부', 60000, '2014-09-22'),
       (7, '한지원', '마케팅팀장', 4, '마케팅부', 58000, '2016-01-30');

-- 3레벨 직원
INSERT INTO Employees
VALUES (8, '송하늘', '소프트웨어 엔지니어', 5, '기술부', 50000, '2018-05-15'),
       (9, '윤서연', '회계사', 6, '재무부', 48000, '2017-08-10'),
       (10, '홍길동', '마케팅 전문가', 7, '마케팅부', 45000, '2019-03-25');

SELECT *
FROM Employees;

WITH OrgChart AS (
    -- 앵커 멤버 (최상위 관리자)
    SELECT EmployeeID,
           Name,
           Position,
           ManagerID,
           Department,
           0                           AS Level,
           CAST(Name AS NVARCHAR(500)) AS HierarchyPath
    FROM Employees
    WHERE ManagerID IS NULL

    UNION ALL

    -- 재귀 멤버
    SELECT e.EmployeeID,
           e.Name,
           e.Position,
           e.ManagerID,
           e.Department,
           oc.Level + 1,
           CAST(oc.HierarchyPath + ' > ' + e.Name AS NVARCHAR(500))
    FROM Employees e
             INNER JOIN OrgChart oc ON e.ManagerID = oc.EmployeeID)
SELECT EmployeeID,
       REPLICATE('    ', Level) + Name AS EmployeeName,
       Position,
       Department,
       Level,
       HierarchyPath
FROM OrgChart
ORDER BY HierarchyPath;

WITH ManagementChain AS (
    -- 앵커 멤버 (시작 직원)
    SELECT EmployeeID,
           Name,
           Position,
           ManagerID,
           Department,
           0 AS Level
    FROM Employees
    WHERE Name = '송하늘'

    UNION ALL

    -- 재귀 멤버 (위로 올라감)
    SELECT e.EmployeeID,
           e.Name,
           e.Position,
           e.ManagerID,
           e.Department,
           mc.Level + 1
    FROM Employees e
             INNER JOIN ManagementChain mc ON e.EmployeeID = mc.ManagerID)
SELECT Level,
       EmployeeID,
       Name,
       Position,
       Department
FROM ManagementChain
ORDER BY Level DESC; -- 최상위부터 표시


CREATE TABLE SalesData
(
    SaleID          INT PRIMARY KEY,
    ProductCategory NVARCHAR(50),
    Region          NVARCHAR(50),
    Quarter         NVARCHAR(10),
    Amount          DECIMAL(12, 2),
    SaleDate        DATE
);

INSERT INTO SalesData
VALUES (1, '전자제품', '서울', 'Q1', 1250000, '2023-01-15'),
       (2, '의류', '부산', 'Q1', 850000, '2023-02-20'),
       (3, '가구', '대구', 'Q1', 620000, '2023-03-10'),
       (4, '전자제품', '인천', 'Q1', 980000, '2023-03-25'),
       (5, '의류', '서울', 'Q2', 1100000, '2023-04-05'),
       (6, '가구', '부산', 'Q2', 730000, '2023-05-18'),
       (7, '전자제품', '대구', 'Q2', 1420000, '2023-06-22'),
       (8, '의류', '인천', 'Q2', 920000, '2023-06-30'),
       (9, '가구', '서울', 'Q3', 680000, '2023-07-12'),
       (10, '전자제품', '부산', 'Q3', 1350000, '2023-08-08'),
       (11, '의류', '대구', 'Q3', 790000, '2023-09-15'),
       (12, '가구', '인천', 'Q3', 540000, '2023-09-28'),
       (13, '전자제품', '서울', 'Q4', 1580000, '2023-10-10'),
       (14, '의류', '부산', 'Q4', 1150000, '2023-11-25'),
       (15, '가구', '대구', 'Q4', 820000, '2023-12-05'),
       (16, '전자제품', '인천', 'Q4', 1020000, '2023-12-20'),
       (17, '전자제품', '서울', 'Q1', 1320000, '2024-01-18'),
       (18, '의류', '부산', 'Q1', 910000, '2024-02-14'),
       (19, '가구', '대구', 'Q1', 710000, '2024-03-08'),
       (20, '전자제품', '인천', 'Q1', 1050000, '2024-03-22'),
       (21, '의류', '서울', 'Q2', 1240000, '2024-04-30'),
       (22, '가구', '부산', 'Q2', 830000, '2024-05-15'),
       (23, '전자제품', '대구', 'Q2', 1470000, '2024-06-10'),
       (24, '의류', '인천', 'Q2', 980000, '2024-06-25');


SELECT *
FROM SalesData;

SELECT ProductCategory, [Q1], [Q2], [Q3], [Q4]
FROM
    (
        SELECT
            ProductCategory,
            Quarter,
            Amount
        FROM SalesData
        WHERE YEAR(SaleDate) = 2023
    ) AS SourceTable
        PIVOT
        (
        SUM(Amount)
        FOR Quarter IN ([Q1], [Q2], [Q3], [Q4])
        ) AS PivotTable
ORDER BY ProductCategory;


SELECT Region, [전자제품], [의류], [가구]
FROM
    (
        SELECT
            Region,
            ProductCategory,
            Amount
        FROM SalesData
        WHERE YEAR(SaleDate) = 2023
    ) AS SourceTable
        PIVOT
        (
        AVG(Amount)
        FOR ProductCategory IN ([전자제품], [의류], [가구])
        ) AS PivotTable
ORDER BY Region;

DECLARE @PivotColumns NVARCHAR(MAX) = '';
DECLARE @SQL NVARCHAR(MAX);
