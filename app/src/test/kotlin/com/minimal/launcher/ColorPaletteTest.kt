package com.minimal.launcher

import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ColorPaletteTest {

    private val ctx = ApplicationProvider.getApplicationContext<android.content.Context>()

    private fun resolve(name: String): Int {
        val id = ctx.resources.getIdentifier(name, "color", ctx.packageName)
        if (id == 0) fail("Color resource not found: $name")
        return ContextCompat.getColor(ctx, id)
    }

    @Test fun `minimal_background is pure black`() = assertEquals(0xFF000000.toInt(), resolve("minimal_background"))
    @Test fun `minimal_panel is 0E0E0E`() = assertEquals(0xFF0E0E0E.toInt(), resolve("minimal_panel"))
    @Test fun `minimal_chip is 1A1A1A`() = assertEquals(0xFF1A1A1A.toInt(), resolve("minimal_chip"))
    @Test fun `tile_phone is forest 1F3D2B`() = assertEquals(0xFF1F3D2B.toInt(), resolve("tile_phone"))
    @Test fun `tile_messages is indigo navy 243349`() = assertEquals(0xFF243349.toInt(), resolve("tile_messages"))
    @Test fun `tile_camera is warm graphite 2A2723`() = assertEquals(0xFF2A2723.toInt(), resolve("tile_camera"))
    @Test fun `tile_claude is terracotta 3D2418`() = assertEquals(0xFF3D2418.toInt(), resolve("tile_claude"))
    @Test fun `minimal_text_primary is white`() = assertEquals(0xFFFFFFFF.toInt(), resolve("minimal_text_primary"))
    @Test fun `minimal_text_muted is 888888`() = assertEquals(0xFF888888.toInt(), resolve("minimal_text_muted"))
    @Test fun `minimal_text_faint is 444444`() = assertEquals(0xFF444444.toInt(), resolve("minimal_text_faint"))
    @Test fun `minimal_icon is white`() = assertEquals(0xFFFFFFFF.toInt(), resolve("minimal_icon"))
}
