import { useRef } from 'react';
import { useRecoilState } from 'recoil';

import { headerState } from '@/store/headerState';
import { isScrollDown, isScrollUp, isMinDeltaScroll } from '@/utils/scrollObserver';

const useHandleHeaderByScroll = () => {
	const [isActiveHeader, setIsActiveHeader] = useRecoilState(headerState);
	const headerElement = useRef<HTMLDivElement>(null);
	const lastScrollTop = useRef(0);
	const minDeltaScroll = useRef(10);

	const handleHeaderViewByScroll = () => {
		const currentScroll = document.documentElement.scrollTop;

		if (!headerElement.current) {
			return;
		}

		if (
			isMinDeltaScroll({
				minDeltaScroll: minDeltaScroll.current,
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

	return { isActiveHeader, handleHeaderViewByScroll, headerElement, setIsActiveHeader };
};

export default useHandleHeaderByScroll;
