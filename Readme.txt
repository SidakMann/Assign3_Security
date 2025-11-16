The original code built SQL queries by putting user input directly into the query string.
This allowed attackers to inject SQL keywords like OR 1=1 and bypass login or extract data.

The fix uses PreparedStatement, which separates the SQL structure from the user input.
The database treats inputs as plain text instead of executable SQL.
This prevents attackers from modifying the query logic, fully blocking SQL injection.


 Verification Steps 
 Attempt to bypass login

Input:

username: admin' OR '1'='1
password: anything


Expected result:
Login fails.
Database receives the literal string, not an injected query.