package com.blablaprincess.springboot_simplejava.runners;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenter;
import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenterDtoFormatter;
import com.blablaprincess.springboot_simplejava.business.common.digitsrepresentation.DigitsRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Profile("!test")
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.main.web-application-type", havingValue = "NONE")
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ArrayCountingAlgorithmsPresenter<Integer> presenter;
    private final ArrayCountingAlgorithmsPresenterDtoFormatter formatter;

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("%nType not a int for out of cycle.%n");
        try (Scanner scanner = new Scanner(System.in)) {
            int number;
            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.printf("%nNumber: ");
                number = scanner.nextInt();

                Integer[] array = DigitsRepresentation.getDigitsArray(number);
                System.out.println(formatter.format(presenter.getAlgorithmsCounts(array)));
            }
        } catch (Exception e) {
            System.out.printf("%nCycle interrupted.%n");
        }
    }

}
