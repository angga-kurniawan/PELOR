package com.example.pelor.PartHome

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.pelor.Service.KategoriData
import com.example.pelor.gemifikasi.KategoriType
import com.example.pelor.gemifikasi.getKategoriMarkers
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
import org.osmdroid.views.overlay.Polygon

@Composable
fun PulauPenyengatMap(
    selectedCategory: KategoriType,
    searchQuery: String,
    onNavigateToDetail: (String) -> Unit
) {
    val kategoriMarkers = getKategoriMarkers()
    val kategori = kategoriMarkers[selectedCategory]

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

                val mapController = controller
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
                    override fun onScroll(event: ScrollEvent?) = false
                    override fun onZoom(event: ZoomEvent?): Boolean {
                        event?.let {
                            val zoomLevel = it.zoomLevel
                            when {
                                zoomLevel < minZoom -> mapController.setZoom(minZoom)
                                zoomLevel > maxZoom -> mapController.setZoom(maxZoom)
                                else -> {}
                            }
                        }
                        return true
                    }
                })
            }
        },
        update = { mapView ->
            mapView.overlays.removeAll { it is Marker || it is Polygon }

            when (kategori) {
                is KategoriData.MarkerList -> {
                    val allPoints = mutableListOf<GeoPoint>()

                    kategori.markers.forEach { item ->
                        val title = mapView.context.getString(item.titleResId)

                        if (searchQuery.isNotBlank() &&
                            !title.contains(searchQuery, ignoreCase = true)
                        ) return@forEach

                        val point = GeoPoint(item.latitude, item.longitude)
                        allPoints.add(point)

                        val drawable = ContextCompat.getDrawable(mapView.context, item.iconResId)
                        if (drawable == null) {
                            Log.e("DrawableError", "Drawable null: $title, resId: ${item.iconResId}")
                            return@forEach
                        }

                        val marker = Marker(mapView).apply {
                            position = point
                            this.title = title
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            icon = drawable
                            setOnMarkerClickListener { _, _ ->
                                onNavigateToDetail(title)
                                true
                            }
                        }

                        mapView.overlays.add(marker)
                    }

                    when (allPoints.size) {
                        1 -> {
                            mapView.controller.setZoom(19.0)
                            mapView.controller.setCenter(allPoints.first())
                        }

                        in 2..Int.MAX_VALUE -> {
                            val box = BoundingBox.fromGeoPoints(allPoints)
                            mapView.zoomToBoundingBox(box, true)
                        }
                    }
                }

                is KategoriData.EventAgenda -> {
                    val allZonePoints = mutableListOf<GeoPoint>()

                    kategori.data.zones.forEach { zone ->
                        val polygon = Polygon().apply {
                            points = zone.points
                            fillPaint.color = zone.fillColor.toArgb()
                            strokeColor = zone.strokeColor.toArgb()
                            strokeWidth = 1f
                            title = "Zona Event"
                        }
                        mapView.overlays.add(polygon)
                        allZonePoints.addAll(zone.points)
                    }

                    kategori.data.marker?.let { markerItem ->
                        val title = mapView.context.getString(markerItem.titleResId)

                        if (searchQuery.isBlank() || title.contains(searchQuery, ignoreCase = true)) {
                            val markerPoint = GeoPoint(markerItem.latitude, markerItem.longitude)
                            val drawable = ContextCompat.getDrawable(mapView.context, markerItem.iconResId)

                            if (drawable != null) {
                                val marker = Marker(mapView).apply {
                                    position = markerPoint
                                    this.title = title
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    icon = drawable
                                    setOnMarkerClickListener { _, _ ->
                                        onNavigateToDetail(title)
                                        true
                                    }
                                }
                                mapView.overlays.add(marker)

                                mapView.controller.setZoom(19.0)
                                mapView.controller.setCenter(markerPoint)
                            }
                        }
                    }

                    if (allZonePoints.isNotEmpty()) {
                        val zoneBoundingBox = BoundingBox.fromGeoPoints(allZonePoints)
                        mapView.zoomToBoundingBox(zoneBoundingBox, true)
                    }
                }

                null -> {
                    Log.w("Map", "Kategori tidak ditemukan: $selectedCategory")
                }
            }

            mapView.invalidate()
        },
        modifier = Modifier.fillMaxSize()
    )
}