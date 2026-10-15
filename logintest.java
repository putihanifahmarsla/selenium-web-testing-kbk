package testing;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.JavascriptExecutor; 


public class logintest {

    public String baseUrl = "http://127.0.0.1:8000/login";
    public WebDriver driver;

    @BeforeTest
    public void setup() {
        System.out.println("sebelum test dieksekusi");
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(baseUrl);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    }

    @Test
    public void LoginTest() throws InterruptedException {
        // cari username
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("superadmin@pnp.ac.id"); //sesuaikan dengan elemen website kalian

        //cari password
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("12345678"); //sesuaikan dengan elemen website kalian

        // tombol login
        driver.findElement(By.xpath("//button[@type='submit']")).submit(); //sesuaikan dengan elemen website kalian

        Thread.sleep(2000);
    }

    @Test(priority = 2, dependsOnMethods = {"LoginTest"})
    public void lihatUser() throws InterruptedException {
    	driver.get("http://127.0.0.1:8000/tampiluser");
        Thread.sleep(3000);
    }
    
    @Test(priority = 3, dependsOnMethods = {"lihatUser"})
    public void insertUser() throws InterruptedException {
        // Arahkan ke halaman tambah user
        driver.get("http://127.0.0.1:8000/users/create");

        // Isi form tambah KBK
        driver.findElement(By.xpath("//input[@id='dosen_id']")).sendKeys("100");
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Puti");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("puti@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("12345678");

        // Submit form
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(2000);
        
        driver.get("http://127.0.0.1:8000/tampiluser");
    }

//   @Test(priority = 3, dependsOnMethods = {"updateUser"})
//    public void updateUser() throws InterruptedException {
//       // Arahkan ke halaman daftar user
//       driver.get("http://127.0.0.1:8000/tampiluser");
//
//       // Temukan baris tabel yang mengandung Dosen id "100"
//      WebElement userRow = driver.findElement(By.xpath("//tr[td[1][text() = '100']]"));
//
//       // Temukan tombol "Edit" dalam baris tersebut
//        WebElement editButton = userRow.findElement(By.xpath(".//a[contains(@class, 'btn btn-warning btn-sm') and contains(text(), 'Edit')]"));
//
//       // Klik tombol "Edit" menggunakan JavascriptExecutor
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);
//       ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
//
//        // Tunggu beberapa detik agar halaman terbuka
//        Thread.sleep(1000);
//
//        // Isi ulang data yang ingin diubah
//        WebElement DosenidInput = driver.findElement(By.xpath("//input[@id='dosen_id']"));
//        DosenidInput.clear();
//        DosenidInput.sendKeys("100");
//
//        WebElement nameInput = driver.findElement(By.xpath("//input[@id='name']"));
//        nameInput.clear();
//        nameInput.sendKeys("Puti");
//
//        WebElement emailInput = driver.findElement(By.xpath("//textarea[@id='email']"));
//        emailInput.clear();
//        emailInput.sendKeys("updated test case");
//    
//		WebElement passwordInput = driver.findElement(By.xpath("//textarea[@id='password']"));
//		passwordInput.clear();
//		passwordInput.sendKeys("updated test case");
//
//        // Submit form
//      driver.findElement(By.xpath("//button[@type='submit']")).submit();
//
//   }


//	   @Test(priority = 4, dependsOnMethods = {"deletetUser"})
//	   public void deleteUser() throws InterruptedException {
//       // Arahkan ke halaman daftar user
//       driver.get("http://127.0.0.1:8000/tampiluser");
//
//       // Temukan baris tabel yang mengandung dosen id "100"
//       WebElement userRow = driver.findElement(By.xpath("//tr[td[1][text()= '100']]"));
//
//       // Temukan tombol "Hapus" dalam baris tersebut
//       WebElement deleteButton = userRow.findElement(By.xpath(".//button[contains(text(), 'Hapus')]"));
//
//       // Scroll ke tombol "Hapus" agar menjadi terlihat dan klik menggunakan JavascriptExecutor
//       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButton);
//       ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);
//
//       // Tunggu hingga alert muncul dan konfirmasi penghapusan
//       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//       wait.until(ExpectedConditions.alertIsPresent());
//       driver.switchTo().alert().accept();
//
//       // Tunggu sebentar untuk pesan sukses muncul setelah penghapusan
//       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-success']")));
//
//       // Verifikasi jika user berhasil dihapus
//       WebElement successMessage = driver.findElement(By.xpath("//div[@class='alert alert-success']"));
//       if (successMessage.getText().contains("User berhasil dihapus")) {
//       	 System.out.println("Hapus User sukses");
//       } else {
//           System.out.println("Hapus User gagal");
//       }
//       
//       Thread.sleep(9000);
//   }

    public void logOut() {
        driver.findElement(By.xpath("//span[@class='http://127.0.0.1:8000/logout']")).click();
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000); // tunggu 5 detik sebelum keluar
        logOut();
        driver.close();
        driver.quit();
    }

    public static void main(String[] args) throws InterruptedException {
        // Menjalankan test case login
        logintest loginTest = new logintest();
        loginTest.setup();
        loginTest.LoginTest();
        loginTest.insertUser();
//        loginTest.updateUser();
        loginTest.deleteUser();
        loginTest.tearDown();
    }
}