package com.example.pelor.PartHome

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.pelor.gemifikasi.kategoriMarkers
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun PulauPenyengatMap(
    selectedCategory: String,
    onNavigateToDetail: (String) -> Unit
) {
    AndroidView(
        factory = { context ->
            Configuration.getInstance().load(
                context,
                context.getSharedPreferences("osmdroid", 0)
            )

            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

//                val centerLatitude = (0.93463 + 0.91964) / 2
//                val centerLongitude = (104.43383 + 104.40134) / 2
                val mapController = controller
//                val startPoint = GeoPoint(centerLatitude, centerLongitude)
                val startPoint = GeoPoint(0.928105, 104.416695)
                mapController.setCenter(startPoint)
                mapController.setZoom(17.0)
                mapOrientation = 90.0f

                val boundingBox = BoundingBox(
                    0.93463, 104.43383,
                    0.91964, 104.40134
                )
                setScrollableAreaLimitDouble(boundingBox)
                val minZoom = 16.0
                val maxZoom = 20.0

                addMapListener(object : MapListener {
                    override fun onScroll(event: ScrollEvent?): Boolean = false

                    override fun onZoom(event: ZoomEvent?): Boolean {
                        event?.let {
                            val zoomLevel = it.zoomLevel
                            if (zoomLevel < minZoom) {
                                mapController.setZoom(minZoom)
                            } else if (zoomLevel > maxZoom) {
                                mapController.setZoom(maxZoom)
                            } else {

                            }
                        }
                        return true
                    }
                })
            }

        },
        update = { mapView ->
            mapView.overlays.removeAll { it is Marker }

            val boundingBox = BoundingBox(
                0.93984, 104.43596,
                0.91637, 104.39743
            )

            val markersToShow = kategoriMarkers[selectedCategory] ?: emptyList()

            markersToShow.forEach { item ->
                val point = GeoPoint(item.latitude, item.longitude)
                if (boundingBox.contains(point)) {
                    val drawable = ContextCompat.getDrawable(mapView.context, item.iconResId)
                    drawable?.let {
                        val marker = Marker(mapView).apply {
                            position = point
                            title = item.title
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            icon = drawable
                            setOnMarkerClickListener { _, _ ->
                                onNavigateToDetail(item.title)
                                true
                            }
                        }
                        mapView.overlays.add(marker)
                    }
                }
            }
            mapView.invalidate()
        },
        modifier = Modifier.fillMaxSize()
    )
}