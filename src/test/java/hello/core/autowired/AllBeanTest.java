package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
//        System.out.println("discountService = " + discountService);
        //hello.core.autowired.AllBeanTest$DiscountService@2f4205be
        //hello.core.autowired.AllBeanTest$DiscountService$$EnhancerBySpringCGLIB$$1515a240@54e22bdd
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        int fixDiscountPolicy = discountService.discount(memberA, 10000, "fixDiscountPolicy");
        System.out.println("fixDiscountPolicy = " + fixDiscountPolicy);

        //then
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixDiscountPolicy).isEqualTo(1000);
    }

    @Configuration
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("Constructor policyMap = " + policyMap);
            System.out.println("Constructor policies = " + policies);
        }
        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
//            System.out.println("discountCode = " + discountCode);
//            System.out.println("discountPolicy = " + discountPolicy);
            return discountPolicy.discount(member, price);
        }
    }
}
