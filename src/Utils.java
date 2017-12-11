import java.net.URL;

public class Utils {

    public static final URL MOUSE_IMAGE_URL = Utils.class.getResource("resources/mouse.png");
    public static final URL MOUSE_TRAPPED_URL = Utils.class.getResource("resources/mouse_trapped.png");
    public static final URL CAT_IMAGE_URL = Utils.class.getResource("resources/cat.png");
    public static final URL CAT_TRAPPED_URL = Utils.class.getResource("resources/cat_trapped.png");
    public static final URL CHEESE_IMAGE_URL = Utils.class.getResource("resources/cheese.png");
    public static final URL TRAP_IMAGE_URL = Utils.class.getResource("resources/trap.png");
    public static final URL MOVABLE_IMAGE_URL = Utils.class.getResource("resources/movable.png");
    public static final URL UNMOVABLE_IMAGE_URL = Utils.class.getResource("resources/unmovable.png");
    public static final URL EMPTY_IMAGE_URL = Utils.class.getResource("resources/empty.png");

   
    public static Config getElementsFromFile(String filePath) throws Exception {

        Config config = null;
        File file;
        FileInputStream fis = null;
        BufferedReader reader = null;
        int maxLineLngth; 
        List<Element> elementsFromConfig = new ArrayList<Element>();


        try {
            file = new File(filePath);

            if (!file.exists() || file.isDirectory()) {
                throw new Exception("File " + filePath + " does not exist or is a directory");

            } else {
                if (!file.canRead()) {
                    throw new Exception("File " + filePath + " can't be read. Do you have sufficient permission?");
                }
            }

            
            fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis));

            String line = reader.readLine();
            maxLineLngth = line.length();

            if (maxLineLngth == 0) {
                throw new Exception("File " + filePath + " contains an empty row.");
            }

            while (line != null) {
                for (char c : line.toCharArray()) {
                    Element e;
                    if (c == ' ') {
                        e = getElementFromChar('e');
                    } else {
                        e = getElementFromChar(c);
                    }
                    elementsFromConfig.add(e);
                }
                line = reader.readLine();
            }

            config = new Config(elementsFromConfig, maxLineLngth);


        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {

                if (reader != null)
                    reader.close();

                if (fis != null)
                    fis.close();

            } catch (IOException e) {
            }
        }

        return config;
    }


    
    private static Element getElementFromChar(char c) {

        Element element = null;
        c = Character.toLowerCase(c);

        switch (c) {
            case 'c':
                element = new Cat();
                break;
            case 'm':
                element = new Mouse();
                break;
            case 'x':
                element = new ConstantCallSite();
                break;
            case 'b':
                element = new MovableBlock();
                break;
            case 't':
                element = new Trap();
                break;
            case 'h':
                element = new Cheese();
                break;
            case 'e':
                element = new Empty();
                break;
            case 'u':
                element = new ConstantBlock();
                break;
        }

        return element;
    }

}