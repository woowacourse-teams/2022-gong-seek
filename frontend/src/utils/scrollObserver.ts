export const isScrollDown = ({
	currentScroll,
	lastScrollTop,
	headerHeight,
}: {
	currentScroll: number;
	lastScrollTop: number;
	headerHeight: number;
}) => currentScroll > lastScrollTop && currentScroll > headerHeight;

export const isScrollUp = ({ currentScroll }: { currentScroll: number }) =>
	currentScroll + document.documentElement.offsetHeight <= document.documentElement.scrollHeight;

export const isMinDeltaScroll = ({
	minDeltaScroll,
	currentScroll,
	lastScrollTop,
}: {
	minDeltaScroll: number;
	currentScroll: number;
	lastScrollTop: number;
}) => Math.abs(lastScrollTop - currentScroll) <= minDeltaScroll;
