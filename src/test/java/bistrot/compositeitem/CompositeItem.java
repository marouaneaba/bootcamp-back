package bistrot.compositeitem;

import com.intuit.karate.junit5.Karate;

class CompositeItem {
    
    @Karate.Test
    Karate testUsers() {
        return Karate.run("compositeItem").relativeTo(getClass());
    }    

}
