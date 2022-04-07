package com.example.shift.adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shift.R;
import com.example.shift.activity.ProductDetailsActivity;
import com.example.shift.activity.ViewAllActivity;
import com.example.shift.model.HomePageModal;
import com.example.shift.model.HorizontalProductScrollModel;
import com.example.shift.model.SliderModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModal> homePageModalList;
    private RecyclerView.RecycledViewPool recycledViewPool;


    public HomePageAdapter(List<HomePageModal> homePageModalList) {
        this.homePageModalList = homePageModalList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModalList.get(position).getType()) {
            case 0:
                return HomePageModal.BANNER_SLIDER;
            case 1:
                return HomePageModal.STRIP_AD_BANNER;
            case 2:
                return HomePageModal.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModal.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePageModal.BANNER_SLIDER:
                View bannerSliderview = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_banner, parent, false);
                return new BannerSliderViewHolder(bannerSliderview);
            case HomePageModal.STRIP_AD_BANNER:
                View stripAdview = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdBannerViewHolder(stripAdview);
            case HomePageModal.HORIZONTAL_PRODUCT_VIEW:
                View  horizontalproductview = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalProductViewHolder(horizontalproductview);
            case HomePageModal.GRID_PRODUCT_VIEW:
                View  gridproductview = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewHolder(gridproductview);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModalList.get(position).getType()) {
            case HomePageModal.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModalList.get(position).getSliderModelList();
                ((BannerSliderViewHolder) holder).setBannersliderViewpager(sliderModelList);
                break;
            case HomePageModal.STRIP_AD_BANNER:
                String resource = homePageModalList.get(position).getResource();
                String color = homePageModalList.get(position).getBackgroundColor();
                ((StripAdBannerViewHolder) holder).setStripAd(resource, color);
                break;
            case HomePageModal.HORIZONTAL_PRODUCT_VIEW:
                String title  = homePageModalList.get(position).getTitle();
                String bgcolor = homePageModalList.get(position).getBackgroundColor();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModalList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollModelList,title,bgcolor);
                break;
            case HomePageModal.GRID_PRODUCT_VIEW:
                String Gridtitle  = homePageModalList.get(position).getTitle();
                String bgcolor2 = homePageModalList.get(position).getBackgroundColor();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModalList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder) holder).setGridProductLayout(gridProductScrollModelList,Gridtitle,bgcolor2);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModalList.size();
    }

    /// Slider List
    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager bannersliderViewpager;
        private int currentpage = 2;
        private Timer timer;
        private final long DELAY_TIME = 3000;
        private final long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannersliderViewpager = itemView.findViewById(R.id.banner_slider_viewpager);
        }

        private void setBannersliderViewpager(final List<SliderModel>sliderModelList){

            currentpage = 2;
            if(timer != null){
                timer.cancel();
            }
            arrangedList = new ArrayList<>();
            for(int x=0; x<sliderModelList.size();x++){
                arrangedList.add(x,sliderModelList.get(x));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
            bannersliderViewpager.setAdapter(sliderAdapter);
            bannersliderViewpager.setClipToPadding(false);
            bannersliderViewpager.setPageMargin(20);
            bannersliderViewpager.setCurrentItem(currentpage);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    currentpage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state == ViewPager.SCROLL_STATE_IDLE){
                        PageLooper(arrangedList);
                    }
                }
            };
            bannersliderViewpager.addOnPageChangeListener(onPageChangeListener);
            startBannerSlideshow(arrangedList);

            bannersliderViewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    PageLooper(arrangedList);
                    stopBannerSlideshow();
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        startBannerSlideshow(arrangedList);
                    }
                    return false;
                }
            });
        }
        private void PageLooper(@NotNull List<SliderModel> sliderModelList) {
            if (currentpage == sliderModelList.size() - 2) {
                currentpage = 2;
                bannersliderViewpager.setCurrentItem(currentpage, false);
            }

            if (currentpage == 1) {
                currentpage = sliderModelList.size() - 3;
                bannersliderViewpager.setCurrentItem(currentpage, false);
            }
        }
        private void startBannerSlideshow(List<SliderModel> sliderModelList) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentpage >= sliderModelList.size()) {
                        currentpage = 1;
                    }
                    bannersliderViewpager.setCurrentItem(currentpage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }
        private void stopBannerSlideshow() {
            timer.cancel();
        }
    }

    /// Strip Ad
    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder {

        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(String resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(stripAdImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    /// Horizontal layout
    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView horizontalLayoutTitle;
        private Button horizontalviewAllbtn;
        private RecyclerView horizontalrecyclerview;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.Container);
            horizontalLayoutTitle = (TextView) itemView.findViewById(R.id.horizontal_scroll_lt_Titile);
            horizontalviewAllbtn = (Button) itemView.findViewById(R.id.horizontal_scroll_lt_viewAllbtn);
            horizontalrecyclerview = itemView.findViewById(R.id.horizontal_scroll_lt_recyclerview);
            horizontalrecyclerview.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title,String bgcolor ){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bgcolor)));
            horizontalLayoutTitle.setText(title);
            if(horizontalProductScrollModelList.size()> 8){
                horizontalviewAllbtn.setVisibility(View.VISIBLE);
                horizontalviewAllbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }else {
                horizontalviewAllbtn.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManagerhorizontal = new LinearLayoutManager(itemView.getContext());
            linearLayoutManagerhorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);

            horizontalrecyclerview.setLayoutManager(linearLayoutManagerhorizontal);
            horizontalrecyclerview.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    // Grid Layout
    public class GridProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout container;
        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllbtn;
        private androidx.gridlayout.widget.GridLayout gridProductLayout;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_titile);
            gridLayoutViewAllbtn = itemView.findViewById(R.id.grid_product_layout_viewAlbtn);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);
            container = itemView.findViewById(R.id.Container);
        }
        private void setGridProductLayout(List<HorizontalProductScrollModel>horizontalProductScrollModelList,String title,String color){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);
            for(int x = 0; x < 4;x++){
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_img);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_titile);
                TextView productDiscription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_porduct_price);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(productImage);
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductTitle());
                productDiscription.setText(horizontalProductScrollModelList.get(x).getProductDescription());
                productPrice.setText("Rs."+horizontalProductScrollModelList.get(x).getProductPrice()+"/-");
                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));

                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }
            gridLayoutViewAllbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                    Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
    }

}