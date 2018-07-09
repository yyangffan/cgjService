package   com.lhkj.cgjservice.base.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

//    final int PAGE_COUNT = 3;
//    private String tabTitles[] = new String[]{"tab1","tab2","tab3"};
    private List<String> tabTitles ;
    private List<Fragment> fragments ;
    private Context context;

    public TabFragmentPagerAdapter(FragmentManager fm, Context context  ,List<Fragment> fragments,List<String> tabTitles) {
        super(fm);
        this.context = context;
        this.tabTitles = tabTitles;
        this.fragments = fragments ;
    }


    @Override
    public Fragment getItem(int position) {
        if(fragments != null &&fragments.size() > position)
            return fragments.get(position);
        else
            return null ;
    }

    @Override
    public int getCount() {
        if(tabTitles!=null)
            return tabTitles.size();
        else
            return 0 ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(tabTitles!=null &&tabTitles.size() > position)
            return tabTitles.get(position);
        else
            return "" ;
    }

}
