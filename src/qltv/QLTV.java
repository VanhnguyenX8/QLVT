/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package qltv;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import qltv.controller.BookController;
import qltv.controller.BorrowingController;
import qltv.controller.LibraryController;
import qltv.controller.MemberController;
import qltv.model.Book;
import qltv.model.Borrowing;
import qltv.model.Data;
import qltv.model.Librarian;
import qltv.model.Member;
import qltv.singleton.JsonSingleTon;

/**
 *
 * @author VanhNguyenX8
 */
public class QLTV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Phần mềm quản lý hệ thống thư viện: \nQuản lý sách bấm phím 1\nQuản lý thành viên bấm phím 2.\nQuản lý thủ thư bấm phím 3.\nQuản lý mượn sách bấm phím 4.\nMời bạn nhập giá trị\n");
        int number = scanner.nextInt();

        switch (number) {
            case 1:
                System.out.println("Quản lý sách bấm phím 1");
                try {
                    // Đọc từ tệp JSON
                    // Singleton
                    JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();
                    JsonObject jsonObject = jsonSingleTon.readJsonFile();
                    Gson gson = new Gson();
                    Data libraryData = gson.fromJson(jsonObject, Data.class);
                    for (int i = 0; i < libraryData.getBooks().size(); i++) {
                        Book book = libraryData.getBooks().get(i);
                        System.out.println("phần tử " + i + " :\n" + book);
                    }
                    System.out.println("Nhập thông tin\n1. để thêm\n2. để xóa\n3 để sửa");
                    System.out.println("4 tim sách theo tên");
                    System.out.println("5 tìm sách theo tác giả");
                    System.out.println("6 tìm sách theo thể loại");
                    System.out.println("7 tìm sách theo mã số sách");

                    Scanner quanLySach = new Scanner(System.in);
                    int numberQuanLySach = quanLySach.nextInt();
                    switch (numberQuanLySach) {
                        case 1:
                            BookController bookController = new BookController();
                            Scanner bookControllerScanner = new Scanner(System.in);
                            System.out.println("Mời bạn nhập");
                            System.out.print("id:");
                            String id = bookControllerScanner.nextLine();
                            System.out.print("name:");
                            String name = bookControllerScanner.nextLine();
                            System.out.print("author:");
                            String author = bookControllerScanner.nextLine();
                            System.out.print("category:");
                            String category = bookControllerScanner.nextLine();
                            System.out.print("count:");
                            int count = bookControllerScanner.nextInt();
                            // new book obj
                            Book newBook = new Book(id, name, author, category, count);
                            System.out.println(newBook);
//                            TODO: add book
                            bookController.add(newBook);

                            break;
                        case 2:
                            Scanner scannerDelete = new Scanner(System.in);
                            BookController bookControllerDelete = new BookController();
                            System.out.println("màn xóa");
                            System.out.print("nhập Id xóa:");
                            String idDelete = scannerDelete.next();
                            if (bookControllerDelete.delete(idDelete)) {
                                System.out.println("Xóa thành công");
                            } else {
                                System.out.println("Lỗi khi xóa");
                            }

                            break;
                        case 3:
                            System.out.println("màn sửa");
                            BookController bookControllerUpdate = new BookController();
                            Scanner scannerUpdate = new Scanner(System.in);
                            System.out.println("Mời bạn nhập id:");
                            String idUpdate = scannerUpdate.nextLine();
                            System.out.print("name:");
                            String nameUpdate = scannerUpdate.nextLine();
                            System.out.print("author:");
                            String authorUpdate = scannerUpdate.nextLine();
                            System.out.print("category:");
                            String categoryUpdate = scannerUpdate.nextLine();
                            System.out.print("count:");
                            int countUpdate = scannerUpdate.nextInt();
                            // new book obj
                            Book bookUpdate = new Book(idUpdate, nameUpdate, authorUpdate, categoryUpdate, countUpdate);
                            System.out.println(bookUpdate);
//                            TODO: add book
                            bookControllerUpdate.update(bookUpdate);
                            break;
                        case 4:

                            System.out.println("Tìm theo tên");
                            System.out.print("Nhập tên");
                            BookController bookControllerFitterName = new BookController();
                            Scanner bookControllerFitterNameScan = new Scanner(System.in);
                            String bookControllerFitterNameInput = bookControllerFitterNameScan.nextLine();
                            Book fiterBookBuyName = new Book();
                            fiterBookBuyName.setName(bookControllerFitterNameInput);
                            List<Book> resultFitterBookName = bookControllerFitterName.fitter(fiterBookBuyName);
                            System.out.println(resultFitterBookName);
                            break;

                        case 5:
                            System.out.println("Tìm theo tác giả");
                            System.out.print("Nhập tên tác giả: ");
                            BookController bookControllerFitterAuthor = new BookController();
                            Scanner bookControllerFitterAuthorScanner = new Scanner(System.in);
                            String bookControllerFitterAuthorInput = bookControllerFitterAuthorScanner.nextLine();
                            Book fiterBookBuyAuthor = new Book();
                            fiterBookBuyAuthor.setAuthor(bookControllerFitterAuthorInput);
                            List<Book> resultFitterBookAuthor = bookControllerFitterAuthor.fitter(fiterBookBuyAuthor);
                            System.out.println(resultFitterBookAuthor);
                            break;
                        case 6:
                            System.out.println("Tìm sách theo thể loại");
                            System.out.print("Nhập tên sách: ");
                            BookController bookControllerFitterCategoty = new BookController();
                            Scanner bookControllerFitterCategotyScanner = new Scanner(System.in);
                            String bookControllerFitterCategotyInput = bookControllerFitterCategotyScanner.nextLine();
                            Book fiterBookBuyCategory = new Book();
                            fiterBookBuyCategory.setAuthor(bookControllerFitterCategotyInput);
                            List<Book> resultbookControllerFitterCategoty = bookControllerFitterCategoty.fitter(fiterBookBuyCategory);
                            System.out.println(resultbookControllerFitterCategoty);
                            break;
                        case 7:

                            System.out.println("tìm sách theo mã số sách");
                            System.out.print("Nhập mã số sách: ");
                            BookController bookControllerFitterCode = new BookController();
                            Scanner bookControllerFitterCodeScanner = new Scanner(System.in);
                            String bookControllerFitterCodeInput = bookControllerFitterCodeScanner.nextLine();
                            Book fiterBookBuyCode = new Book();
                            fiterBookBuyCode.setAuthor(bookControllerFitterCodeInput);
                            List<Book> resultbookControllerFitterCode = bookControllerFitterCode.fitter(fiterBookBuyCode);
                            System.out.println(resultbookControllerFitterCode);
                            break;

                        default:
                            System.out.println("nhập lại từ 1 đến 3");

                    }

                    // nhap case va chon chinh sua book
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case 2:
                System.out.println("Màn quản lý thành viên");
                //TODO: show member
                JsonSingleTon jsonSingleTonMember = JsonSingleTon.getInstance();
                JsonObject jsonObject = jsonSingleTonMember.readJsonFile();
                Gson gson = new Gson();
                Data memberData = gson.fromJson(jsonObject, Data.class);
                for (int i = 0; i < memberData.getMembers().size(); i++) {
                    Member member = memberData.getMembers().get(i);
                    System.out.println("phần tử " + i + " :\n" + member);
                }
                System.out.println("1 Thêm thành viên");
                System.out.println("2 Xóa thành viên chọn");
                System.out.println("3 Sửa thành viên chọn");
                System.out.println("4 Tìm theo tên");
                System.out.println("5 Tìm theo mã");
                MemberController controller = new MemberController();
                Scanner scannerMember = new Scanner(System.in);
                int numberMember = scanner.nextInt();
                switch (numberMember) {
                    case 1:
                        System.out.println("màn thêm");
                        MemberController memberController = new MemberController();
                        Scanner memberControllerAdd = new Scanner(System.in);
                        System.out.println("Mời bạn nhập id:");
                        String idNew = memberControllerAdd.nextLine();
                        System.out.print("name:");
                        String nameNew = memberControllerAdd.nextLine();
                        System.out.print("address:");
                        String addressNew = memberControllerAdd.nextLine();
                        System.out.print("số sánh đã mượn nhập các nhau dấu ,:");
                        String borrwingsNew = memberControllerAdd.nextLine();
                        // new book obj
                        Member member = new Member(addressNew, Arrays.asList(borrwingsNew.split(",")), nameNew, idNew);
                        System.out.println(member);
                        memberController.add(member);
                        break;
                    case 2:
                        Scanner MemberDeleteScanner = new Scanner(System.in);
                        MemberController memberControllerDelete = new MemberController();
                        System.out.println("màn xóa");
                        System.out.print("nhập Id xóa: ");
                        String idDelete = MemberDeleteScanner.next();
                        if (memberControllerDelete.delete(idDelete)) {
                            System.out.println("Xóa thành công");
                        } else {
                            System.out.println("Lỗi khi xóa");
                        }

                        break;
                    case 3:
                        System.out.println("vào màn sửa");
                        MemberController memberControllerUpdate = new MemberController();
                        Scanner memberControllerUpdateScanner = new Scanner(System.in);
                        System.out.println("Mời bạn nhập id:");
                        String memberControllerUpdateInputId = memberControllerUpdateScanner.nextLine();
                        System.out.print("name:");
                        String memberControllerUpdateInputName = memberControllerUpdateScanner.nextLine();
                        System.out.print("address:");
                        String memberControllerUpdateInputAddress = memberControllerUpdateScanner.nextLine();
                        System.out.print("số sánh đã mượn nhập các nhau dấu ,:");
                        String memberControllerUpdateInputBorrwing = memberControllerUpdateScanner.nextLine();
                        // new book obj
                        Member memberUpdate = new Member(memberControllerUpdateInputAddress, Arrays.asList(memberControllerUpdateInputBorrwing.split(",")), memberControllerUpdateInputName, memberControllerUpdateInputId);
                        System.out.println(memberUpdate);
                        memberControllerUpdate.update(memberUpdate);
                        break;
                    case 4:
                        System.out.println("Tìm theo tên");
                        System.out.print("Nhập tên: ");
                        MemberController memberControllerFitterName = new MemberController();
                        Scanner memberControllerFitterNameScanner = new Scanner(System.in);
                        String memberControllerFitterNameInput = memberControllerFitterNameScanner.nextLine();
                        Member newMemberName = new Member();
                        newMemberName.setName(memberControllerFitterNameInput);
                        List<Member> resultmemberControllerFitterName = memberControllerFitterName.fitter(newMemberName);
                        System.out.println(resultmemberControllerFitterName);
                        break;
                    case 5:
                        System.out.println("Tìm theo mã");
                        System.out.print("Nhập mã: ");
                        MemberController memberControllerFitterCode = new MemberController();
                        Scanner memberControllerFitterCodeScanner = new Scanner(System.in);
                        String memberControllerFitterCodeInput = memberControllerFitterCodeScanner.nextLine();
                        Member newMemberCode = new Member();
                        newMemberCode.setId(memberControllerFitterCodeInput);
                        List<Member> resultmemberControllerFitterCode = memberControllerFitterCode.fitter(newMemberCode);
                        System.out.println(resultmemberControllerFitterCode);
                        break;
                    default:
                        System.out.println("Nhập lại");
                }
                break;

            case 3:
                JsonSingleTon jsonSingleTonLibrarian = JsonSingleTon.getInstance();
                JsonObject jsonObjectLibrarian = jsonSingleTonLibrarian.readJsonFile();
                Gson gsonLibrarian = new Gson();
                Data libraryData = gsonLibrarian.fromJson(jsonObjectLibrarian, Data.class);
                for (int i = 0; i < libraryData.getLibrarians().size(); i++) {
                    Librarian librarian = libraryData.getLibrarians().get(i);
                    System.out.println("phần tử " + i + " :\n" + librarian);
                }
                System.out.println("Màn quản lý thủ thư");
                System.out.println("1 Thêm thủ thư chọn");
                System.out.println("2 Xóa thủ thư chọn");
                System.out.println("3 Sửa thủ thư chọn");
                System.out.println("4 Tìm thủ thư theo tên chọn");
                System.out.println("5 Tìm thủ thư theo mã chọn");
                Scanner quanLyThuThu = new Scanner(System.in);
                int numberQuanLyThuThu = quanLyThuThu.nextInt();

                switch (numberQuanLyThuThu) {
                    case 1:
                        System.out.println("Thêm thủ thư!");
                        Scanner quanLyThuThuAdd = new Scanner(System.in);
                        LibraryController libraryController = new LibraryController();
                        System.out.println("Mời bạn nhập: ");
                        System.out.print("id:");
                        String id = quanLyThuThuAdd.nextLine();
                        System.out.print("tên:");
                        String name = quanLyThuThuAdd.nextLine();
                        System.out.print("ca làm viêc:");
                        String shift = quanLyThuThuAdd.nextLine();
                        Librarian newLibrarian = new Librarian(shift, name, id);
                        System.out.println(newLibrarian);
//                            TODO: add book
                        libraryController.add(newLibrarian);

                        break;
                    case 2:
                        System.out.println("Xóa thủ thư");
                        System.out.print("Nhập id xóa: ");
                        Scanner quanLyThuThuDelete = new Scanner(System.in);
                        String idDelete = quanLyThuThuDelete.nextLine();
                        LibraryController libraryControllerDelete = new LibraryController();
                        if(libraryControllerDelete.delete(idDelete)) {
                            System.out.println("Xóa thành công");
                        } else {
                            System.out.println("Lỗi khi xóa");
                        }
                    case 3:
                        System.out.println("Sửa thủ thư:");
                        System.out.print("Nhập id thủ thư cần sửa: ");
                        LibraryController libraryControllerUpdate = new LibraryController();
                        Scanner quanLyThuThuUpdate = new Scanner(System.in);
                        String idUpdate = quanLyThuThuUpdate.nextLine();
                        System.out.print("tên: ");
                        String nameThuThuUpdate = quanLyThuThuUpdate.nextLine();
                        System.out.print("ca làm viêc: ");
                        String shiftUpdate = quanLyThuThuUpdate.nextLine();
                        Librarian updateLibrarian = new Librarian(shiftUpdate, nameThuThuUpdate, idUpdate);
                        System.out.println(updateLibrarian);
                        libraryControllerUpdate.update(updateLibrarian);
                    case 4:
                        System.out.println("Tìm theo tên: ");
                        System.out.print("Nhập tên: ");
                        LibraryController libraryControllerfiterName = new LibraryController();
                        Scanner quanLyThuThuFiterName = new Scanner(System.in);
                        String quanLyThuThuFiterNameInput = quanLyThuThuFiterName.nextLine();
                        List<Librarian> resultFitterName = libraryControllerfiterName.fiterWithIdOrCode(quanLyThuThuFiterNameInput, "");
                        System.out.println(resultFitterName);
                        break;
                    case 5:
                        System.out.println("Tìm theo mã: ");
                        System.out.print("Nhập mã: ");
                        LibraryController libraryControllerfiterCode = new LibraryController();
                        Scanner quanLyThuThuFiterCode = new Scanner(System.in);
                        String quanLyThuThuFiterCodeInput = quanLyThuThuFiterCode.nextLine();
                        List<Librarian> resultFitterCode = libraryControllerfiterCode.fiterWithIdOrCode("", quanLyThuThuFiterCodeInput);
                        System.out.println(resultFitterCode);
                        break;
                    default:
                        System.out.println("Nhập lại");
                }
                break;
            case 4:
                System.out.println("Mượn sách!");
                Scanner scannerBorrowing = new Scanner(System.in);
                System.out.println("Chọn 1 để mượn sách.");
                System.out.println("Chọn 2 để trả sách.");
                System.out.println("Chọn 3 để lưu trữ thông tin. ");

                String numberBorrowing = scannerBorrowing.nextLine();

                switch (Integer.parseInt(numberBorrowing)) {
                    case 1:
                        System.out.println("Màn mượn sách");
                        System.out.print("Nhập id user mượn sách: ");
                        Scanner scannerBorrowing1 = new Scanner(System.in);
                        String numberBorrowing1IdUser = scannerBorrowing1.nextLine();
                        System.out.println("Nhập id sách cần mượn: ");
                        String numberBorrowing1IDSach = scannerBorrowing1.nextLine();
                        System.out.println("Nhập ngày trả sách(dd/MM/yyyy): ");
                        String endDateInput = scannerBorrowing1.nextLine();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        try {
                            Date endDate = dateFormat.parse(endDateInput);
                            // Định dạng lại ngày theo định dạng "dd/MM/yyyy" để hiển thị
                            BorrowingController borrowingController1 = new BorrowingController();
                            if (borrowingController1.borrowBook(numberBorrowing1IdUser, numberBorrowing1IDSach, endDate)) {
                                System.out.println("Thành công");
                            } else {
                                System.out.println("Đã sảy ra lỗi khi mượn sách");
                            }
                        } catch (Exception e) {
                            System.out.println("Đã sảy ra lỗi khi nhập ngày: " + e);
                        }
                        break;
                    case 2:
                        System.out.println("Màn trả sách");
                        Scanner scannerBorrowing3 = new Scanner(System.in);

                        System.out.print("Nhập id sách trả: ");
                        String numberBorrowing3IdBook = scannerBorrowing3.nextLine();
                        System.out.print("Nhập id người trả");
                        String numberBorrowing3IdUser = scannerBorrowing3.nextLine();
                        BorrowingController borrowingControllerUpdate = new BorrowingController();
                        String result = borrowingControllerUpdate.updateBorrowingMemberAndBook(numberBorrowing3IdUser, numberBorrowing3IdBook);
                        if (result == "late") {
                            System.out.println("Bạn đã trả muộn hơn hạn");
                        } else {
                            System.out.println("Bạn đã trả đúng hạn");
                        }

                    //TODO: update tra hoac da tra, update trạng thái trả muộn hay không
                    //remove idSach trong user nếu như có hạn, còn k có hạn thì sử lý sau
                    case 3:
                        System.out.println("màn xem thông tin mượn trả sách");
//                        TODO: show data in table borrwings
                        BorrowingController borrowingControllerShow = new BorrowingController();
                        List<Borrowing> resultBorrowing = borrowingControllerShow.showBorrowing();
                        for (int i = 0; i < resultBorrowing.size(); i++) {
                            System.out.println(resultBorrowing.get(i));
                        }
                    default:
                        System.out.println("Hãy nhập lại");
                }
            default:
                System.out.println("Số bạn nhập không nằm trong khoảng từ 1 đến 4.");
                break;
        }
        scanner.close();
    }
}
