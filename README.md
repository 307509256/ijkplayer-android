1: 本工程使用ffmpeg软解， 库是使用armv7a，放置在ijkplayer-armv7a目录下。
2：工程中有两个硬解库，exo是google提供的硬解库，mediacodec是Android系统的硬解库（暂时不需要用到）。

应用类的变化:java/tv/danmaku/ijk/media/example/widget/media/IjkVideoView.java：

修改处：
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 0);         

增加：
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max_cached_duration", 3000); //max set to 3000ms
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "infbuf", 1);  //unlimited
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);// close buf
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "probesize", "524288");  //if <1080P  maybe set to value<512*1024 eg: 720P->262144
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");