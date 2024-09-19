
# Coverage Report: JaCoCo

* IntegrationTest (QuarkusTest)
      
      
| Outcome                 | Value                                                               |
|-------------------------|---------------------------------------------------------------------|
| Code Coverage %         | 99.12%               |
| :heavy_check_mark: Number of Lines Covered | 225    |
| :x: Number of Lines Missed  | 2     |
| Total Number of Lines   | 227     |


## Details:

    
### it/gov/pagopa/atmlayer/transaction/service/mapper

<details>
    <summary>
:heavy_check_mark: TransactionMapperImpl.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: TransactionMapper.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/exception

<details>
    <summary>
:heavy_check_mark: AtmLayerException.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/model

<details>
    <summary>
:x: PageInfo.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ATMLayerErrorResponse.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ATMLayerValidationErrorResponse.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/service

<details>
    <summary>
:x: TransactionService.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service

<details>
    <summary>
:heavy_check_mark: App.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/exception/mapper

<details>
    <summary>
:heavy_check_mark: GlobalExceptionMapperImpl.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/configuration

<details>
    <summary>
:heavy_check_mark: HttpRequestLogger.java
    </summary>

        
#### All Lines Covered!
        
- Line #16
```
        String uri = requestContext.getUriInfo().getAbsolutePath() != null ? Encode.forJava(requestContext.getUriInfo().getAbsolutePath().toString()) : null;
```
- Line #18
```
        String headers = requestContext.getHeaders() != null ? Encode.forJava(requestContext.getHeaders().toString()) : null;
```
</details>

    

<details>
    <summary>
:heavy_check_mark: HttpResponseLogger.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/dto

<details>
    <summary>
:x: TransactionDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: TransactionUpdateDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: TransactionInsertionDTO.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/resource

<details>
    <summary>
:heavy_check_mark: TransactionResource.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/service/impl

<details>
    <summary>
:x: TransactionServiceImpl.java
    </summary>

        
#### Lines Missed:
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/utils

<details>
    <summary>
:x: ConstraintViolationMappingUtils.java
    </summary>

        
</details>

    

<details>
    <summary>
:heavy_check_mark: ConstraintViolationMappingUtilsImpl.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/enums

<details>
    <summary>
:heavy_check_mark: AppErrorType.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: AppErrorCodeEnum.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/entity

<details>
    <summary>
:x: TransactionEntity.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/transaction/service/repository

<details>
    <summary>
:x: TransactionRepository.java
    </summary>

        
#### Lines Missed:
        
</details>

    
