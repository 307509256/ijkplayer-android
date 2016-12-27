1: 本工程使用ffmpeg软解， 库是使用armv7a，放置在ijkplayer-armv7a目录下。
2：工程中有两个硬解库，exo是google提供的硬解库，mediacodec是Android系统的硬解库（暂时不需要用到）。

应用类的变化:java/tv/danmaku/ijk/media/example/widget/media/IjkVideoView.java：

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//gdebug modify 修改处：
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 0);//48 to 0

//增加代码：
Log.e(TAG, "createPlayer: gdebug(java) add option for live......\n");

/* 减少出画面的时间
* 直播必选，点播可选
*/
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "probesize", "524288");  //if <1080P  maybe set to value<512*1024 eg: 720P->262144

/* 如果应用不设置，就交给底层判断( 如果项目全是直播，可以设置);
* 如果应用可以肯定是直播，就设置成1，点播设置成0
* 底层是通过总时长是否为0来判断直播或点播
*/
//                  ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "islive", 1);  //live
//                  ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "islive", 0);  //vod

/* 如果设置成4000，会流畅一些，实时性弱一些；
*  设置成3000，实时性好一些，流畅性差一些； 一般是（2000-5000之间）
*  直播必选，点播不要设置
*/
//                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max_cached_duration", 4000);

/* 无限制收流
* rtsp直播必须设置成1， 点播不用设置
*/
//                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "infbuf", 1);  //unlimited

/* 是否缓冲
* rtsp直播必须设置成0， 点播不用设置
*/
//                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);

/* rtsp 传输方式，如果不设，默认是先udp再tcp
* rtsp直播点播选上后，可能会加快连接速度，因为有的流用默认的udp连不上，再切回tcp就浪费了时间
*/
//                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");

//test for example

//judge by app 
boolean  islive = true;             //eg: url.contains("live") && url.contains("rtsp://")

if(true){   //rtsp live  如果项目都是rtsp的，就不用判断url是否包含了“rtsp://”
  Log.e(TAG, "gdebug(java) live......\n");
  ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max_cached_duration", 3000);
  ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "infbuf", 1);  //unlimited
  ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
  ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
}else{  //rtsp vod
  Log.e(TAG, "gdebug(java) vod......getDuration = \n" + ijkMediaPlayer.getDuration());
  ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////