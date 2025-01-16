package com.example.demo.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountInfo;
import com.example.demo.dto.BankResponse;
import com.example.demo.dto.CreditDebitRequest;
import com.example.demo.dto.EmailDetails;
import com.example.demo.dto.EnquiryRequest;
import com.example.demo.dto.TransactionDto;
import com.example.demo.dto.TransferRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRespository;
import com.example.demo.utils.AccountUtils;

@Service
public class UserServiceImplementation implements UserService {
        // se inyectan todos los metodos del repositorio
        @Autowired
        UserRespository userRespository;

        @Autowired
        EmailService emailService;

        @Autowired
        TransactionService transactionService;

        @Override
        public BankResponse createAccount(UserRequest userRequest) {
                // TODO Auto-generated method stub
                // creating an account -saving a new user into database
                // check if user already has an account

                if (userRespository.existsByEmail(userRequest.getEmail())) {
                        // verifica si correo ya registrado
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                                        .accountInfo(null)
                                        .build();

                } // si no esta registrado, se crea nuevo objeto de tipo User newUser
                User newUser = User.builder()
                                .firstName(userRequest.getFirstName())
                                .lastName(userRequest.getLastName())
                                .otherName(userRequest.getOtherName())
                                .gender(userRequest.getGender())
                                .address(userRequest.getAddress())
                                .stateOfOrigin(userRequest.getStateOfOrigin())
                                .accountNumber(AccountUtils.generateAccountNumber())
                                .accountBalance(BigDecimal.ZERO)
                                .email(userRequest.getEmail())
                                .phoneNumber(userRequest.getPhoneNumber())
                                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                                .status("ACTIVE")// valor predeterminado cuando se crea la cuenta
                                .build();

                // save into database
                User savedUser = userRespository.save(newUser);

                // send email alert
                EmailDetails emailDetails = EmailDetails.builder()
                                .recipient(savedUser.getEmail())
                                .subject("Account Creation")
                                .messageBody("Congratulations! your account has been succesfully created\n Your Account Details: \n"
                                                +
                                                "Account Name: " + savedUser.getFirstName() + " "
                                                + savedUser.getLastName() + " " + savedUser.getOtherName()
                                                + "\nAccount Number: " + " " + savedUser.getAccountNumber())
                                .build();
                emailService.sendEmailAlert(emailDetails);

                // se envia respuesta con los datos proporcionados
                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCES)
                                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                                .accountInfo((AccountInfo.builder()
                                                .accountBalance(savedUser.getAccountBalance())
                                                .accountNumber(savedUser.getAccountNumber())
                                                .accountName(
                                                                savedUser.getFirstName() + " " + savedUser.getLastName()
                                                                                + " "
                                                                                + savedUser.getOtherName()))
                                                .build())
                                .build();

        }

        @Override
        public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
                // check if the provided account number exists in the db

                boolean isAccountExist = userRespository.existsByAccountNumber(enquiryRequest.getAccountNumber());

                if (!isAccountExist) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                                        .accountInfo(null)
                                        .build();
                }

                User foundUser = userRespository.findByAccountNumber(enquiryRequest.getAccountNumber());
                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_FOUND)
                                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                                .accountInfo(AccountInfo.builder()
                                                .accountBalance(foundUser.getAccountBalance())
                                                .accountNumber(enquiryRequest.getAccountNumber())
                                                .accountName(foundUser.getFirstName() + " " + foundUser.getLastName()
                                                                + " " + foundUser.getOtherName())
                                                .build())
                                .build();

        }

        @Override
        public String nameEnquiry(EnquiryRequest enquiryRequest) {

                boolean isAccountExist = userRespository.existsByAccountNumber(enquiryRequest.getAccountNumber());
                if (!isAccountExist) {
                        return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
                }
                User foundUser = userRespository.findByAccountNumber(enquiryRequest.getAccountNumber());
                return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
        }

        @Override
        public BankResponse creditAccount(CreditDebitRequest request) {
                boolean isAccountExist = userRespository.existsByAccountNumber(request.getAccountNumber());
                // checking if the account exists
                if (!isAccountExist) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                                        .accountInfo(null)
                                        .build();

                }
                User userToCredit = userRespository.findByAccountNumber(request.getAccountNumber());
                userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
                userRespository.save(userToCredit);
                // save transaction
                TransactionDto transactionDto = TransactionDto.builder()
                                .accountNumber(userToCredit.getAccountNumber())
                                .transactionType("CREDIT")
                                .amount(request.getAmount())
                                .build();

                transactionService.saveTransaction(transactionDto);

                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                                .accountInfo(AccountInfo.builder()
                                                .accountName(userToCredit.getFirstName() + " "
                                                                + userToCredit.getLastName() + " "
                                                                + userToCredit.getOtherName())
                                                .accountBalance(userToCredit.getAccountBalance())
                                                .accountNumber(request.getAccountNumber())
                                                .build())
                                .build();
        }

        @Override
        public BankResponse debitAccount(CreditDebitRequest request) {
                // check if the account exists
                // check if the amount you intend to withdraw is not more than
                // the current account balance
                boolean isAccountExist = userRespository.existsByAccountNumber(request.getAccountNumber());
                // checking if the account exists
                if (!isAccountExist) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                                        .accountInfo(null)
                                        .build();

                }
                User userToDebit = userRespository.findByAccountNumber(request.getAccountNumber());

                BigInteger availableBalance = (userToDebit.getAccountBalance().toBigInteger());
                BigInteger debitAmount = request.getAmount().toBigInteger();
                if (availableBalance.intValue() < debitAmount.intValue()) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                                        .accountInfo(null)
                                        .build();

                }

                else {
                        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
                        userRespository.save(userToDebit);

                        // save transaction
                        TransactionDto transactionDto = TransactionDto.builder()
                                        .accountNumber(userToDebit.getAccountNumber())
                                        .transactionType("DEBIT")
                                        .amount(request.getAmount())
                                        .build();

                        transactionService.saveTransaction(transactionDto);
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                                        .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                                        .accountInfo(AccountInfo.builder()
                                                        .accountNumber(request.getAccountNumber())
                                                        .accountName(userToDebit.getFirstName() + " "
                                                                        + userToDebit.getLastName() + " "
                                                                        + userToDebit.getOtherName())
                                                        .accountBalance(userToDebit.getAccountBalance())
                                                        .build())
                                        .build();
                }
        }

        @Override
        public BankResponse transfer(TransferRequest request) {
                // get the account to debit(check if it exists)
                // check if the amount i'm debiting is not more than the current balance
                // debit the account
                // get the account to credit
                // credit the account

                boolean isDestinationAccountExist = userRespository
                                .existsByAccountNumber(request.getDestinationAccountNumber());

                if (!isDestinationAccountExist) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                                        .accountInfo(null)
                                        .build();
                }

                User sourceAccountUser = userRespository.findByAccountNumber(request.getSourceAccountNumber());
                if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                                        .accountInfo(null)
                                        .build();

                }
                sourceAccountUser
                                .setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
                String sourceUsername = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName() + " "
                                + sourceAccountUser.getOtherName();
                userRespository.save(sourceAccountUser);
                EmailDetails debitAlert = EmailDetails.builder()
                                .subject("Debit Alert")
                                .recipient(sourceAccountUser.getEmail())
                                .messageBody("The sum of " + request.getAmount() +
                                                "has been deducted from your account. Your current balance is " +
                                                sourceAccountUser.getAccountBalance())
                                .build();

                emailService.sendEmailAlert(debitAlert);

                User destinationAccountUser = userRespository
                                .findByAccountNumber(request.getDestinationAccountNumber());
                destinationAccountUser
                                .setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));

                // String recipientUsername = destinationAccountUser.getFirstName()+"
                // "+destinationAccountUser.getLastName()+"
                // "+destinationAccountUser.getOtherName();

                userRespository.save(destinationAccountUser);
                EmailDetails creditAlert = EmailDetails.builder()
                                .subject("Credit Alert")
                                .recipient(destinationAccountUser.getEmail())
                                .messageBody("The sum of " + request.getAmount()
                                                + "has been sent from your account from " + sourceUsername
                                                + "Your current balance is " + sourceAccountUser.getAccountBalance())
                                .build();
                emailService.sendEmailAlert(creditAlert);

                // save transaction
                TransactionDto transactionDto = TransactionDto.builder()
                                .accountNumber(destinationAccountUser.getAccountNumber())
                                .transactionType("CREDIT")
                                .amount(request.getAmount())
                                .build();

                transactionService.saveTransaction(transactionDto);

                return BankResponse.builder()
                                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                                .responseMessage(AccountUtils.TRANSFER_SUCCESFUL_MESSAGE)
                                .accountInfo(null)
                                .build();
        }

}
