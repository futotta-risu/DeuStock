# List of API functions

## Auth

- auth
  - login/{username}/{password}
  - register

## Investment

//- twitter/sentiment/{query}

Wallet
- holdings/{username}/
    - balance
  - holdings
  - holdings/reset

Operation
- stock
  - operation/open/{operation}/{symbol}/{username}/{amount}
  - operation/close/{stockHistoryID} 

Stock
- stock
  - detail/{query}/{interval}
  - list/{size}

- reports/{stock}/{interval}

## Users

- users
  - details/change
  - delete/{username}/{password}

- user
  - {username}
  - {username}/holdings
  - {username}/holdings/reset
  
## Help

- help/faq/list
- helloworld