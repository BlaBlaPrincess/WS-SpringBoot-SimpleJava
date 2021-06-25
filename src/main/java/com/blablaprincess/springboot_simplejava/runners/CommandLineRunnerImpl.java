package com.blablaprincess.springboot_simplejava.runners;

import com.blablaprincess.springboot_simplejava.business.arraycounting.presenters.ArrayCountingAlgorithmsPresenter;
import com.blablaprincess.springboot_simplejava.business.digitsrepresentation.DigitsRepresentation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Profile("!test")
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ArrayCountingAlgorithmsPresenter<Integer> presenter;

    @Autowired
    public CommandLineRunnerImpl(ArrayCountingAlgorithmsPresenter<Integer> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
        presenter.setup(new Integer[]{})
                 .withAlgorithmsCount()
                 .withSeparator()
                 .withAlgorithmsList();
        System.out.println(presenter);

        System.out.printf("%n=========================%n%n");
        System.out.println("Type not a int for out of cycle.");

        var scanner = new Scanner(System.in);
        int number;
        while (true) {
            System.out.printf("%nNumber: ");

            try {
                number =  scanner.nextInt();
            } catch (Exception e){
                break;
            }

            var array = DigitsRepresentation.getDigitsArray(number);
            presenter.setup(array).withCounts();
            System.out.println(presenter);
        }

        System.out.printf("%nCycle interrupted.%n");
    }

}
