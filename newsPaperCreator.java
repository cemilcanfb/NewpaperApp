 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent(); //get Intent to create the document
        numberOfNews = intent.getIntExtra(getString(R.string.NumberOfNews),5);
        int numberOfCol = intent.getIntExtra(getString(R.string.NumberOfCol),2);
        boolean overTitle = intent.getBooleanExtra(getString(R.string.OverTitle),false);
        boolean underTitle = intent.getBooleanExtra(getString(R.string.UnderTitle),true);
        String username = intent.getStringExtra(getString(R.string.welcomeName));

        textViewArray = new TextView[numberOfNews];
        textViewArrayContent = new TextView[numberOfNews];
        imageView = new ImageView[numberOfNews];
        Websites = new String[numberOfNews];

        String[] WebsitesIds = getResources().getStringArray(R.array.Websites);
        for (int i = 0;i<numberOfNews;i++){
            Websites[i] =  intent.getStringExtra(WebsitesIds[i]);
        }
        //For each news create its title,content, and image holder
        for (int i=0;i<numberOfNews;i++){
            textViewArray[i] = new TextView(this);
            String name_TV = "CreateNews_TextView" + (i+1);
            textViewArray[i].setId(this.getResources().getIdentifier(name_TV,"id",this.getPackageName()));
            textViewArray[i].setTextSize(20);
            textViewArray[i].setTypeface(Typeface.DEFAULT_BOLD);
            textViewArray[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    gotoWebsite(v);
                }
            });

            textViewArrayContent[i] = new TextView(this);
            String name_TV_cont = "CreateNews_TextViewContent" + (i+1);
            textViewArrayContent[i].setId(this.getResources().getIdentifier(name_TV_cont,"id",this.getPackageName()));
            textViewArrayContent[i].setTextSize(12);

            imageView[i] = new ImageView(this);
            String name_iv = "CreateNews_ImageView" + (i+1);
            imageView[i].setId(this.getResources().getIdentifier(name_iv,"id",this.getPackageName()));
            imageView[i].setScaleType(ImageView.ScaleType.FIT_START);
        }
        //Create newspaper's title
        TextView newspaper_title = new TextView(this);
        String name_title = "CreateNews_Newspaper_Title";
        newspaper_title.setId(this.getResources().getIdentifier(name_title,"id",this.getPackageName()));
        newspaper_title.setTextSize(25);
        newspaper_title.setTypeface(Typeface.DEFAULT_BOLD);
        newspaper_title.setText(username+"'s newspaper");




        setContentView(R.layout.activity_news_paper_creator);
        RelativeLayout currLayout = (RelativeLayout) findViewById(R.id.activity_news_paper_creator);
        RelativeLayout[] news_layouts = new RelativeLayout[numberOfNews];
        int numberOfLinLayouts = numberOfNews;
        //If there are over title remove one news from the set
        if (overTitle)
            numberOfLinLayouts--;
        //If there are under title remove one news from the set
        if (underTitle)
            numberOfLinLayouts--;
        //Determine number of rows in the layout
        if ((numberOfLinLayouts%numberOfCol)>0){
            numberOfLinLayouts/=numberOfCol;
            numberOfLinLayouts++;
        } else {
            numberOfLinLayouts/=numberOfCol;
        }
        LinearLayout[] news_outlier = new LinearLayout[numberOfLinLayouts];
        int currLL = 0;

        int numInCol = numberOfCol;
        int previousId = 0;
        boolean content_right = true;

        //For each news place it to the corresponding place in the layout.
        for (int i=0;i<numberOfNews;i++){
            news_layouts[i] = new RelativeLayout(this);
            news_layouts[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            String name_layout = "CreateNews_Layout" + (i+1);
            news_layouts[i].setId(this.getResources().getIdentifier(name_layout,"id",this.getPackageName()));
            if (i==0 && overTitle){
                //If there are overtitle, place it first.
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                currLayout.addView(news_layouts[i],params);
                content_right = true;
                //Place the title after overtitle.
                RelativeLayout.LayoutParams params_title_newspaper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params_title_newspaper.addRule(RelativeLayout.BELOW,news_layouts[i].getId());
                currLayout.addView(newspaper_title,params_title_newspaper);
                previousId = newspaper_title.getId();
            } else if (i==0 && underTitle){
                //If there are no overtitle and undertitle, place the title first, then place under title
                RelativeLayout.LayoutParams params_title_newspaper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params_title_newspaper.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                currLayout.addView(newspaper_title,params_title_newspaper);
                content_right = true;

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW,newspaper_title.getId());
                currLayout.addView(news_layouts[i],params);
                previousId = news_layouts[i].getId();
            } else if (i==1 && overTitle && underTitle) {
                //If there are over and under title place the undertitle after the title.
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                content_right = true;
                params.addRule(RelativeLayout.BELOW, previousId);
                currLayout.addView(news_layouts[i], params);
                previousId = news_layouts[i].getId();
            }  else {
                if (i==0){
                    //No over and under title, place the title
                    RelativeLayout.LayoutParams params_title_newspaper = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params_title_newspaper.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                    currLayout.addView(newspaper_title,params_title_newspaper);
                    previousId = newspaper_title.getId();
                }
                content_right = false;
                if (numInCol == numberOfCol){
                    //Place the layout for the news for the current row.
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW,previousId);
                    news_outlier[currLL] = new LinearLayout(this);
                    String name_layout_LL = "CreateNews_Layout_LL" + (currLL+1);
                    news_outlier[currLL].setId(this.getResources().getIdentifier(name_layout_LL,"id",this.getPackageName()));
                    news_outlier[currLL].setOrientation(LinearLayout.HORIZONTAL);
                    news_outlier[currLL].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    currLayout.addView(news_outlier[currLL],params);

                }
                LinearLayout.LayoutParams params_LL = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params_LL.weight = 1;
                news_outlier[currLL].addView(news_layouts[i],params_LL);

                numInCol--;
                if (numInCol == 0){
                    //Set the number of column to default and prepare it for the next row.
                    numInCol = numberOfCol;
                    previousId = news_outlier[currLL].getId();
                    currLL++;
                }

            }

            RelativeLayout.LayoutParams params_title = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params_title.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
            news_layouts[i].addView(textViewArray[i],params_title);

            RelativeLayout.LayoutParams params_image = new RelativeLayout.LayoutParams(360, 360);
            params_image.addRule(RelativeLayout.BELOW,textViewArray[i].getId());
            news_layouts[i].addView(imageView[i],params_image);

            RelativeLayout.LayoutParams params_content = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (content_right) {
                params_content.addRule(RelativeLayout.BELOW,textViewArray[i].getId());
                params_content.addRule(RelativeLayout.RIGHT_OF,imageView[i].getId());
            } else {
                params_content.addRule(RelativeLayout.BELOW,imageView[i].getId());
            }
            news_layouts[i].addView(textViewArrayContent[i],params_content);


        }

         new TitleExtractorHelper().execute(Websites);






    }


