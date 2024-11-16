//package synerjs.lookkit2nd.user;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//@ToString
//@Table(name = "address_book")
//public class AddressBook {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long addressId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private String addressName;
//
//    @Builder
//    public AddressBook(User user, String addressName) {
//        this.user = user;
//        this.addressName = addressName;
//    }
//}
