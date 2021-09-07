package com.example.easymap.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.easymap.Map;
import com.example.easymap.MapItem;
import com.example.easymap.MapObject;
import com.example.easymap.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class CustomView extends View {

    private Canvas canvas;
    // All the maps with same name
    private ArrayList<Map> maps;
    // All the map object in map
    private ArrayList<MapObject>[] mapObjects;
    // All the map item in map
    private ArrayList<MapItem>[] mapItems;
    // All the Rectangle in map
    private ArrayList<Rect>[] rects;
    // All the paint in rectangle
    private ArrayList<Paint>[] paints;
    private int currentFloor;
    // Check whether the object is ready to be searched or not
    private boolean objectReady;
    //private boolean itemReady;


    //_______________________________________________
    //Init code
    //_______________________________________________
    public CustomView(Context context) {
        super(context);

        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        currentFloor = 1;
        objectReady = false;

        maps = new ArrayList<>();
    }

    //__________________________________________________________________________
    //Graphical code
    //__________________________________________________________________________

    public void changeFloor(int floor) {this.currentFloor = floor;}

    public void setUpMapObject(ArrayList<MapObject> mapObjectsGet, int floor) {

        mapObjects[floor] = mapObjectsGet;

        rects[floor] = new ArrayList<Rect>();
        paints[floor] = new ArrayList<Paint>();

        if(floor == 0)
        {
            this.objectReady = true;
        }

        invalidate();
        //postInvalidate();
    }

    public void setUpMapItem(ArrayList<MapItem> mapItemsGet, int floor) {
        mapItems[floor] = mapItemsGet;

        /*if(floor == (maps.size() - 1))
        {
            this.itemReady = true;
        }*/
    }

    public void setUpCanvas(ArrayList<Map> mapsGet) {
        this.maps = mapsGet;
        this.rects = (ArrayList<Rect>[]) new ArrayList[maps.size()];
        this.paints = (ArrayList<Paint>[]) new ArrayList[maps.size()];
        this.currentFloor = 0;

        this.mapObjects = (ArrayList<MapObject>[]) new ArrayList[maps.size()];
        this.mapItems = (ArrayList<MapItem>[]) new ArrayList[maps.size()];
    }

    @Override
    protected void onDraw(Canvas canvasInit) {
        this.canvas = canvasInit;

        if (objectReady)
        {
            Rect rect;
            Paint paint;

            for (int i = 0; i < mapObjects[currentFloor].size(); i++)
            {
                rect = new Rect();
                rect.left = mapObjects[currentFloor].get(i).getLocation_x().intValue();
                rect.right = rect.left + mapObjects[currentFloor].get(i).getWidth_x().intValue();
                rect.top = mapObjects[currentFloor].get(i).getLocation_y().intValue();
                rect.bottom = rect.top + mapObjects[currentFloor].get(i).getWidth_y().intValue();

                paint = new Paint(Paint.ANTI_ALIAS_FLAG);

                if (mapObjects[currentFloor].get(i).getHighligt())
                {
                    paint.setColor(getResources().getColor(R.color.colorAccent));
                }
                else
                {
                    paint.setColor(getResources().getColor(R.color.colorPrimary));
                }

                rects[currentFloor].add(rect);
                paints[currentFloor].add(paint);

                canvas.drawRect(rect,paint);
            }
        }
    }

    //__________________________________________________________________________
    //Search code
    //__________________________________________________________________________
    public void searchContainString(String searchString){
        ArrayList<Integer>[] containString = (ArrayList<Integer>[]) new ArrayList[2];;

        if (objectReady)
        {
            containString[0] = findFromObjects(searchString);
        }

        invalidate();
    }

    private ArrayList<Integer> findFromObjects(String searchString)
    {
        ArrayList<Integer> floorFind = new ArrayList<>();

        for (int i = 0; i < maps.size(); i++)
        {
            for (int j = 0; j < mapObjects[i].size(); j++)
            {
                if(mapObjects[i].get(j).getDetail().contains(searchString)
                        ||
                        mapObjects[i].get(j).getName().contains(searchString))
                {
                    mapObjects[i].get(j).setHighligt(true);
                    floorFind.add(i);
                }
                else
                {
                    mapObjects[i].get(j).setHighligt(false);
                }
            }
        }
        return floorFind;
    }

    /*private MapObject findObjectFromObjectID(String objectID) {
        for (int i = 0; i < mapObjects.length; i++)
        {
            for (int j = 0; j < mapObjects[i].size(); j++)
        }

        return null;
    }*/

    private ArrayList<Integer> findFromItems(String searchString) {
        ArrayList<String> catalogueList;
        ArrayList<Integer> floorFind = new ArrayList<>();

        for (int i = 0; i < maps.size(); i++)
        {
            for (int j = 0; j < mapItems[i].size(); j++)
            {
                if(mapItems[i].get(j).getName().contains(searchString) ||
                        mapItems[i].get(j).getDescription().contains(searchString)
                )
                {
                    //Set highlight for object that contain this item
                    floorFind.add(i);
                }
                else
                {
                    catalogueList = mapItems[i].get(i).getCategory();
                    for (int k = 0; k < catalogueList.size(); k++)
                    {
                        if (catalogueList.get(i).contains(searchString))
                        {
                            mapObjects[currentFloor].get(i).setHighligt(true);
                        }
                    }
                    mapObjects[currentFloor].get(i).setHighligt(false);
                }
            }
        }
        return floorFind;
    }
}
