package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
class EshopApplicationTest {

    @Test
    void main_runsWithNonWebApplication() {
        EshopApplication.main(new String[]{"--spring.main.web-application-type=none"});
    }
}
