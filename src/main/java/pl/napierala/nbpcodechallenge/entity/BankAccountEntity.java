package pl.napierala.nbpcodechallenge.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_account")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "user_code", nullable = false)
    private String userCode;

    @NotEmpty
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    /**
     * The balance in cents meaning that $10.50 will be 1050 as it is in cents.
     * The concept of cents is just a name as it could be grosz in PLN, pennies for the pound or euro cents.
     */
    @NotNull
    @Column(name = "balance_in_cents", nullable = false)
    private Long balanceInCents;

    /**
     * ISO 4127 currency code, EUR, PLN for example.
     */
    @NotEmpty
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccountEntity that = (BankAccountEntity) o;

        return accountNumber != null ? accountNumber.equals(that.accountNumber) : that.accountNumber == null;
    }

    @Override
    public int hashCode() {
        return accountNumber != null ? accountNumber.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BankAccountEntity{" +
                "id=" + id +
                ", userCode='" + userCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balanceInCents=" + balanceInCents +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}