package se.cyclic.analyzer.dummypackage;

import se.cyclic.analyzer.dummypackage2.JustForTesting2;

public class JustForTesting {
    private JustForTesting2 partOfCyclicDependency = new JustForTesting2();
}
