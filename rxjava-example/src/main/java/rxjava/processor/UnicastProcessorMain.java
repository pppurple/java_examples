package rxjava.processor;

import io.reactivex.processors.UnicastProcessor;
import rxjava.base.DebugSubscriber;

public class UnicastProcessorMain {
    public static void main(String[] args) {
        UnicastProcessor<Integer> processor = UnicastProcessor.create();

        processor.subscribe(new DebugSubscriber<Integer>("No.1"));

        processor.onNext(1);
        processor.onNext(2);
        processor.onNext(3);

        System.out.println("add Subscriber No.2");
        processor.subscribe(new DebugSubscriber<Integer>("--No.2"));

        processor.onNext(4);
        processor.onNext(5);

        processor.onComplete();

        System.out.println("add Subscriber No.3");
        processor.subscribe(new DebugSubscriber<Integer>("----No.3"));
    }
}
