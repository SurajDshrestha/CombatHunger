//package com.aashish.dell.findmearent.volleycustomlistview;
//
//        import android.content.Context;
//        import android.graphics.Bitmap;
//        import android.support.v4.util.LruCache;
//
//        import com.android.volley.Cache;
//        import com.android.volley.Network;
//        import com.android.volley.RequestQueue;
//        import com.android.volley.toolbox.BasicNetwork;
//        import com.android.volley.toolbox.DiskBasedCache;
//        import com.android.volley.toolbox.HurlStack;
//        import com.android.volley.toolbox.ImageLoader;
//
///**
// * Created by Dell on 02-Apr-16.
// */
//public class VolleyImagerRequest {
//
//        private static VolleyImagerRequest VolleyImagerRequest;
//        private static Context context;
//        private RequestQueue requestQueue;
//        private ImageLoader imageLoader;
//
//
//        private VolleyImagerRequest(Context context) {
//            this.context = context;
//            this.requestQueue = getRequestQueue();
//
//            imageLoader = new ImageLoader(requestQueue,
//                    new ImageLoader.ImageCache() {
//                        private final LruCache<String, Bitmap>
//                                cache = new LruCache<String, Bitmap>(20);
//
//                        @Override
//                        public Bitmap getBitmap(String url) {
//                            return cache.get(url);
//                        }
//
//                        @Override
//                        public void putBitmap(String url, Bitmap bitmap) {
//                            cache.put(url, bitmap);
//                        }
//                    });
//        }
//
//        public static synchronized VolleyImagerRequest getInstance(Context context) {
//            if (VolleyImagerRequest == null) {
//                VolleyImagerRequest = new VolleyImagerRequest(context);
//            }
//            return VolleyImagerRequest;
//        }
//
//        public RequestQueue getRequestQueue() {
//            if (requestQueue == null) {
//                Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
//                Network network = new BasicNetwork(new HurlStack());
//                requestQueue = new RequestQueue(cache, network);
//                requestQueue.start();
//            }
//            return requestQueue;
//        }
//
//        public ImageLoader getImageLoader() {
//            return imageLoader;
//        }
//
//    }
