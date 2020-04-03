package kz.yeltayev.aqms.utils

import ru.terrakok.cicerone.Navigator

/**
 * Navigator holder interface.
 * Use it to connect a {@link Navigator} to the {@link Cicerone}.
 */
interface NavigatorHolder {
    /**
     * Set an active Navigator for the Cicerone and start receive commands.
     *
     * @param navigator new active Navigator
     */
    fun setNavigator(navigator: Navigator?)

    /**
     * Remove the current Navigator and stop receive commands.
     */
    fun removeNavigator()
}
