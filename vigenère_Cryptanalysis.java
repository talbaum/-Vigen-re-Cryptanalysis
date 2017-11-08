
public class vigenère_Cryptanalysis {
    public static String cipher (String plainText , String key){ // function for ciphering
        char c; //cipher letter
        String ans=""; //the ciphered text
        for(int i=0;i<plainText.length();i++){ //ciphering process
            c= (char)(( ((plainText.charAt(i)-'A') +
                    (key.charAt(i%key.length()) - 'A'))%26 )+'A' );
            ans=ans+c; //appending the ciphered char to the ciphered text
        }
        return ans;
    }

    public static String decipher (String cipheredText , String key){ // function for ciphering
        char p; //decipher letter
        String ans=""; //the deciphered text
        for(int i=0;i<cipheredText.length();i++){ //deciphering process
            p= (char)(( ((cipheredText.charAt(i)-'A') -
                    (key.charAt(i%key.length()) - 'A')+26)%26 )+'A' );
            ans=ans+p; //appending the deciphered char to the deciphered text
        }
        return ans;
    }

    public static int[] numOfLetters( String text){
        int[] lettersAppearence = new int[26];
        int index;
        for(int i=0;i<text.length();i++) {
            index=text.charAt(i) -'A';
            if(index>=0 && index<26)
                lettersAppearence[index]++;
        }
        return lettersAppearence;
    }

    public static double icForOneElement(String cipheredText) {
        int[] lettersAppearance = numOfLetters(cipheredText);
        double sum = 0,multiple,divider,ans;
        for (int i = 0; i < 26; i++)
            sum+=lettersAppearance[i]*(lettersAppearance[i]-1);

        multiple = cipheredText.length() * (cipheredText.length() - 1);
        divider=multiple/26;
        ans= sum/divider;
        return ans;
    }

    public static double icForAverage(String cipheredText, int moduloSize) {
        double[] icModouloArr = new double[moduloSize];
        String columnStr;
        double sum = 0;

        for (int i = 0; i < moduloSize; i++) {
            columnStr="";
            for (int j = i; j < cipheredText.length(); j += moduloSize) {
                columnStr+=cipheredText.charAt((j));
            }
            icModouloArr[i] = icForOneElement(columnStr);
        }

        for (int i = 0; i < moduloSize; i++)
            sum+= + icModouloArr[i];


        return sum / moduloSize;
    }

    public static int findLengthOfKey(String cipheredText) {
        int maxLengthOfKey;
        if (cipheredText.length() < 15)
            maxLengthOfKey = cipheredText.length();
        else
            maxLengthOfKey = 15;

        double[] avgOfAllKeys = new double[maxLengthOfKey + 1];   // we place the keys from 1 to max length +1 because
        for (int i = 1; i <= maxLengthOfKey; i++)                 // at icForAverage at the second for we increment in this size
            avgOfAllKeys[i] = icForAverage(cipheredText, i);      //and we dont want to incrememnet with 0 -> endless loop.

        return findMin(avgOfAllKeys);
    }

    public static int findMin(double[] ics){
        double min=ics[0];
        int index=0;
        for(int i=1;i<ics.length;i++){
            if( Math.abs(ics[i]-1.73)<Math.abs(min-1.73)) {
                min = ics[i];
                index = i;
            }
        }
        return index;
    }

    public static String findTheKey(String cipheredText){
        int keyLength=findLengthOfKey(cipheredText);
        String columnStr;
        double sum;
        int u=0;
        char[] ans= new char[keyLength];
        int [] numOfLettersInColumn;
        double [] corolations=new double[26]; //for every letter
        double [] frequency={0.08167,0.01492,0.02782,0.04253,0.12702,0.02228,0.02015,0.06094,0.06966,0.00153,0.00772,0.04025,0.02406,0.06749,0.07507,0.01929,0.00095,0.05987,0.06327,0.09056,0.02758,0.00978,0.02360,0.00150,0.01974,0.00074};

        for(int k=0; k<keyLength;k++){ //runs over every column
            for(int i=0;i<26;i++){ //check for each ABC letter which one has the best coralation value
                columnStr="";
                sum=0;
                for(int j=k;j<cipheredText.length();j+=keyLength){ //goes over the specific letter of the column
                    int c=cipheredText.charAt(j)-i;
                    if (c < 'A') 
                        c = 'Z' - '@' + c;
         
                    columnStr+=(char)c;
                }
                numOfLettersInColumn=numOfLetters(columnStr);
                for(int t=0;t<26;t++){
                    sum+=(numOfLettersInColumn[t]*frequency[t]);
                }
                corolations[i]=sum;
            }
            char ansChar=(char) (findMax(corolations)+'A');
            ans[u]=ansChar;
            u++;

        }
        String returnMe= new String(ans);
        return returnMe;
    }

    public static int findMax(double[] corolations) {
        double max=corolations[0];
        int indexOfMax=0;
        for(int i=1;i<corolations.length;i++){
            if(corolations[i]>max) {
                max = corolations[i];
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }

    public static void main (String[] args){
        String str="HUGVUKSATTMUNDKUMKVVAYVLPOMCEDTBGKIIEYARTREEDRINKFSMEMQNGFEH" +
                "UVMAMHRUCPVVHBWMOGYZXVJWOMKBMAIELJVRPOMCEDRBWKIUNZEEEFRRPKMA" +
                "ZZYUDZRYRALVRZGNFLEKAKTVGNEJOAWBFLSEEBIAMSCIAKTVGNVRPKMAZHXD" +
                "YXLNFIIIDJSEMPWJOHIIBZMKOMMZNAXVRZHGTWTZNBEGFFGYAHFRKKSFRJRY" +
                "RALZSVRQGVXYIIKZHYIRHYMFMPRTTGCVKLQVMWIEBAARSDRGALFCEVOQXJID" +
                "BZVNGKIRCCWRIHVRTZHLBUKVMWIEPYSLGCXVMZKYONXHIVRKHZJYHVVVABIE" +
                "EFMNINLRWALVMJVEHDZRIIPLBOEUSJYTAAXFBJVEHDJIOHQLUVSBSNYEVLEJ" +
                "EJJFNYVFWNSEKVAWOMXUXSSJTGIAHYIWOMXUXYEIEVRQKHHZAIXZTPHVNRLB" +
                "FALVAIKREZRRMZPRGVVVNVQRELWJHZVRYVVVVZVZHYIRNYXUXZMCKZRFTKYE" +
                "CZVGTPRIUNXYBUKFFZEPAWYIPGIPNYXRIIXUKPPCEYQRYPPCEYQRPPXYFVRG" +
                "TZXZCOIEKVVJNZZRKMICTWISHYIJOOLNMUSNTJWGBSPKHZFRTAMEGJJZROIR" +
                "ROMFMVSURZTRTAMEGOMFLVQVVDWVMVVVNOVRTAMEGZRGKHRTEVXZRJLRMWIE" +
                "WVSISJQREHXVVDWVMVVVNOVRTAMEGZRGKHRTEVXZRJLRMWIEWVSITCMFBZMK" +
                "AIHAHALZNBQBKLTIENIAMSCDYNSHENVVWNXEHUKVRCIFBAEKIIKGALREOGSA" +
                "ZLVJIMWNBKMFRHEQTTXIUGCLHBVWOMKVOLRVSNMVFWPFRZFHMALVFVGGBZMN" +
                "ANRNIWMEGVRQLVKVNOPLRVYTAHIETWTZNBEAWZSWADRGEFCFUXEZXAEGPDRT" +
                "MHTGIIKNMTCTHVQOXYHFOMXUTAMJCVVPXDEJSPVRBOIRRYCBNOIIEDSCXUIU" +
                "WDHRMOIUOJVQTYOEENWGALVVAIHAHALZNBQBKLHVEKMAMVXYEYEEDUIJSKIR" +
                "KPRXLJRTBZXFOYXUXYINOIHRKPRXFZEEBUKUOPFGBUKURZEZBUKURZEZLUSD" +
                "OMXNEZIMEMHNKLHKOYVRTTFVFJVRUBXKHZWVELRTEREFNUFIOFIATUHKHZWG" +
                "BSPEENWTTCIEOOSXXUEEDOLRHUPPWJVQMOIIENTBDLRNANXUXDLZSKIEXKAF" +
                "RYPRGVVVTCMFBDLZSKIEXKEEDVRRVOSDUMQHKLHSAXOGALAFRYPRGVVVMZVR" +
                "EFXYINEAWUSKHDRTFVVVBVGXBUXFTCIPAHQSEMXHKUMEGVPYFFWFUGAVMOME" +
                "MZFHKUMEGNSBGHKRIIMUXHVUAOECIPRXSJQRMOMEGGSHWLVKHVROXMSIENYE" +
                "XSCJADHVLBVVLTXUTAMJSJQRMOMEGVXZRDMEDJAYTAXZCZPRMTIJEZXUXUAY" +
                "AOXUXYIRTDWNGKXYINQLLAIIYZBCEVVVLZXZROIRROFRLAMCLVQBFLRKAIHG" +
                "APWDYNXRKFIOPGSEXAMJTCIJBUHRNYRBMOMEGHSEXVTVNCIEXPJCUIKGALWY" +
                "UOXRKDLVNRMGATEEYVJYBYXRNYJYNAXVRDRGALVVSOICILHRSOEGXSCIAQIA" +
                "HMXYENEVGAPPDVCFHMCFRZRBMALVLZEFMVFVINEAVLQRDZLRGVXRMDRHMLWK" +
                "OKTRWVVJTVCRWOISUOAVMOQZEISSEVVUOMPNWFTVRXLRWHFFVZQLVOEDBZVQ" +
                "HVVGEMGUXKYGOIEONZXFFKEYEHWAUNXNUVZVMTGUTTFVRYSBKWIICCIQTUHJ" +
                "AOEAWUSKHDRTFVVVTCIAMOMJEWSARIMIDWITNPPZNBQLLHHWAIGLBUXFSHMY" +
                "BUKSYOLRZYEMEVRQLAIINYIPHYYDOAXUXJSLNOIATUGVIOABKLXYOPKUMOCT" +
                "RZWGULWYOMRNGKWYAQIAMOSLINEVWHVKSPVRGVGIAQIAZOEJTGCTKPQRNYEA" +
                "VPIETMEIXUARNYIEBUKWRJQGALRZGCXYRZLFRZXRESQVWCEGMOICOMHYRUED" +
                "EDWBGALVNDKUMZTCUOSABHRJHJVRJBSKHOLRKHZVNIIIXYQFRZQHVOMDAMZR" +
                "ESIUTCMFNUKRIIPLYVACTJLRTYHZSXSHKZIJOKPNBUPPTCSHZOMKSVRFPLVC" +
                "IOXYXTIRNDRTEPXKLZVRELZRNXCOHYIWOMARVHREOOLREWEXRZIVGNXYAORB" +
                "EPZZNBLHFHRSEDRTXCIIYZXJTZFCENWRWDMKHNIRBUKSIMHNUVZVHDWPAHQS" +
                "EMHBHYFZRYSEULEJTPTBGALVSXYYIAYIEYFHLAESOQIUBZGYAHFRKKSFRRMG" +
                "AZYTHIEZXHWEEQIEFVVVBPXGALVRVZRFBAXZNBPBGLPPOIXUTATCAXMQUBWK" +
                "SKSXXVRCYOLNMVRVWJVQTZMWHDWFHBPZNOLNMVRVWJVQALHZDJYGIVYINJXU" +
                "BUKWUMXUXYXYEILRNAXVRZHAHAEWEVXUXYXYEILRYSYKTZVRWAMCLDWPTYGV" +
                "LTQBKLXYAIQHMAIIEYSGALVWRDIAWZLRVZJYHDRSEASEXVRKHZQBKYSNHZAV" +
                "ESPVAQIZXHWDYCSCXZLRVZJYHDRSEASEXALVNOLRUPVUSVMQGLZVRHSEXZXR" +
                "ROPRWHXKHZWGBSPEENWOKVOVNWCEXWPPSJECMSCJPJORGKSLBOPRLZWRIYMJ" +
                "AHXZTPXGXYWZSDXFHUPPSOSPDHRUSOSEXJELGCXSKVQJOHIHGOEGPTQNLAII" +
                "WCSZNUQVRXMSNSHZSVWGXYJFLGSJXKJRSOEAWMSCLJARWMEJTZVGBSPYINWB" +
                "GNWFNZFHKKIEBJVRMPPCTCIQBYKVSJJUBZLFPZXUTAQVLVRPAVPPBPVQXUFF" +
                "RZSSGLZVRIIIXYQFRZFHMALVRVZRGZXZLGFRZBMCIIKNESQPFVRPRPRKONQV" +
                "EPRXSOVNBNLKIRLRXSIUAXYFAPSEEYWRTAMEFMSAMVJSIMHNGKFLSOEAWKSF" +
                "ROLRGBTFNOLROLPMEOWVGRMEGDFRMVSBMTWREMXFLDRXBUKWAIGLNUXFFVRP" +
                "RALZNFMAZDLRTOLVLVQZNJYFUPVUOACBKLAYAOXUBZKIIHYAZHMELTKUTZXC" +
                "YBEHGAEEDJQVGVYJBDVQHMCFRZQRTUXZNXVBTRMEGIIIXYQFRZXUNZMJAOIA" +
                "ZHKVDDRTNLWJIIKONARFSTPYTIPVESTEXZWZNBXBMOIWORPJAVWVFDIERLCV" +
                "SISJUBVEEYMAMVQPBJWBFZGFRZXUBZEEDHSEXPWRTYMIBUMEGRMGATCYEVHN" +
                "MLEJEMIPEPRZNBSAMOITUNLVHUWMEGZRMSMEIIKGAHXKHZPNFWPZGCXTEVEK" +
                "EYSRKIYKWCSFXCICVZXIBVPVTGMABUKNIOLGALPRMKPVZOXXLJEGBUKFEMWU" +
                "XZLRLGTEXZWRHIIIXYQFRZXUXUQVTCSHZOXKHZEVKNVVWYIALLVGEMJHFLHW" +
                "RJQNGBRJEZRPXUWVRNAHGNFPSZVNIOMDWCSFXMSFTAEYEZXZNFPRWVRKHZXH" +
                "YAIUFGSBKDVVTXLVVYMVDOLLZVHYAOLYXUXKHZIORALVSZEAZLPJHZLNMOWV" +
                "NOXUXLVVSKMGXYIJPDXRTUHEEKIAMOIWRJQGAFQVMJVVXZSWLZRBKLULAAJB" +
                "JBEWFOLVLRMEDIICXUXYEVRQYVVXEOXUBZPFSOPRGVVVQPSGAALVRVZRGUIM" +
                "EMQBKLTIOKLRMZEZDDXUBUKFFZZVEWVFPCIGLAMCLDJOBYHFRYIIBSAYEOLR" +
                "KAIDPOIELLRKOMAUXALVROIZILWKTJWFXKXYEZLRKLEJHJVRWLWFLVXRRLXR" +
                "LGYAWHYETZHBGALZSYIFXYXCAIHRGJLRNOIQHUXYINLBFLFPHJVEHYLRUIXR" +
                "WAICLHIGKBPPIDQCEVVVINXUXYIZSOLRKLFRLHMAZPPVAYXRESQVTZPYFLMZ" +
                "MKPBKLULOOLGALVRVZRAXCIIMJVRIYSGHZXFTPHZTCMAZVJVVDPCKVTYEOWG" +
                "BSPZFWMEWVVUEQMYUFXYAOLRTCIETCEGULRUSVFBOLYJBTXUTAKFDRIOHALR" +
                "DJVRMLPCTCMFLVYCWDXULVVIORPNWLRZFRMGAPRKHZHVLAEETVMQXURZTNLN" +
                "ESGCANTNLHMETZHZTPHVNRLBFALVAIKREZRRMZPRGVVVCGEFIHVRRZEAWYEU" +
                "IVRGFHMUEIAUHTXYEVRTXSWEAHIYXUSIELYBMOXYEMEIXURVVZVZHYISEOLN" +
                "MDSIDJYELPKEOATNKAMEGWMEWVVWIZRQBZLIIZORWBTJTVVGBUKXEOXUXLFR" +
                "CFMAMVXYEOIZILWKAIHGALRZGCXFISYKOIMNGZLFRZPRTCIEOWPNVRTCUHIN" +
                "LHXFKZRBYALRTGMRMOCJOPPUTALJPJORGSIRVZQLEVRVLDRRLZYEBMSXXUUL" +
                "IOXUXIYJTVFBOLQPDJSEMHOVTCCOXHOWRJQBNAQPHZEEMHRUTVORMOCWOMQS" +
                "KVQFFAQLWVSIQPSGAALVRVZRGUIMEMQBKLEEDOLRKHZVNIIIXYJCIOXVGNWK" +
                "IGPVLZMKTDRTLAMCLDWFBAXZNBSAMOIGAGPVWIYJTJJCTSPRSEYFMHFFVZQL" +
                "VOEDBZVQHVVRNYLVLLCVSCEIXHPCTCIFXLQZNBSSTKIDOIWGAHXZSYVRTTME" +
                "GVRQMOICAHTYBNLKOZVUBTWKRZEZBUKKHMSJLALVSCEQHDSETCISEVSIAIHZ" +
                "RZSLLAVBFVYKTCEGLOEUORXUTAPZENJYHHXZNBSAMOIWLJSELOECLWIYBMXV" +
                "DIIIXYQFRZ";

       
       double length=findLengthOfKey(str);
       String key=findTheKey(str);
        System.out.println("long key length is " + length);
        System.out.println("long key  is " + key);
        System.out.println("The plain text is: ");
        System.out.println((decipher(str,key)));
    }

}
