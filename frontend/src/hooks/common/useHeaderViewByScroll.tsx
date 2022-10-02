import { useRef, useState } from 'react';

import { isScrollDown, isScrollUp, isMinDeltaScroll } from '@/utils/scrollObserver';

const useHeaderViewByScroll = () => {
	const [isActiveHeader, setIsActiveHeader] = useState(true);
	const headerElement = useRef<HTMLDivElement>(null);
	const lastScrollTop = useRef(0);
	const minDelatScroll = useRef(10);

	const handleHeaderViewByScroll = () => {
		const currentScroll = document.documentElement.scrollTop;
		if (!headerElement.current) {
			return;
		}

		if (
			isMinDeltaScroll({
				minDeltaScroll: minDelatScroll.current,
				currentScroll,
				lastScrollTop: lastScrollTop.current,
			})
		) {
			return;
		}

		if (
			isScrollDown({
				currentScroll,
				lastScrollTop: lastScrollTop.current,
				headerHeight: headerElement.current.offsetHeight,
			})
		) {
			setIsActiveHeader(false);
			return;
		}

		if (isScrollUp({ currentScroll })) {
			setIsActiveHeader(true);
		}

		lastScrollTop.current = currentScroll;
	};

	return { isActiveHeader, handleHeaderViewByScroll, headerElement };
};

export default useHeaderViewByScroll;
