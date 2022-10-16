import useDebounce from './useDebounce';
import { useState, useCallback, useEffect, useRef } from 'react';

import { DEBOUNCE_RESIZE_TIME, DEBOUNCE_SCROLL_TIME } from '@/constants';

const MIN_CAROUSEL_INDEX = 0;
const MAX_CAROUSEL_INDEX = 9;

const useCarousel = () => {
	const [carouselElementRef, setCarouselElementRef] = useState<null | HTMLDivElement>(null);
	const [currentIndex, setCurrentIndex] = useState(0);
	const [isScroll, setIsScroll] = useState(false);
	const timerId = useRef<number | null>();
	const carouselItemWidth = useRef<number>(0);

	const setCarouselItemWidth = useDebounce(() => {
		if (carouselElementRef) {
			carouselItemWidth.current = carouselElementRef.scrollWidth / 15;
		}
	}, DEBOUNCE_RESIZE_TIME);

	const handleScrollEnd = useCallback(() => {
		setIsScroll(true);
		if (timerId.current !== null) {
			clearTimeout(timerId.current);
		}
		timerId.current = window.setTimeout(function () {
			setIsScroll(false);
		}, DEBOUNCE_SCROLL_TIME);
	}, [timerId.current]);

	useEffect(() => {
		if (carouselElementRef) {
			carouselItemWidth.current = carouselElementRef.scrollWidth / 15;
			carouselElementRef.addEventListener('scroll', handleScrollEnd);
			window.addEventListener('resize', setCarouselItemWidth);

			carouselElementRef.scrollTo({
				left: carouselItemWidth.current,
				behavior: 'auto',
			});
		}

		return () => {
			carouselElementRef?.removeEventListener('scroll', handleScrollEnd);
			window.removeEventListener('resize', setCarouselItemWidth);
		};
	}, [carouselElementRef]);

	const handleLeftSlideEvent = () => {
		if (currentIndex === MIN_CAROUSEL_INDEX || isScroll) {
			return;
		}

		carouselElementRef?.scrollBy({
			left: -carouselItemWidth.current,
			behavior: 'smooth',
		});
		setCurrentIndex((prev) => prev - 1);
	};

	const handleRightSlideEvent = () => {
		if (isScroll) {
			return;
		}

		if (currentIndex === MAX_CAROUSEL_INDEX) {
			setCurrentIndex(MIN_CAROUSEL_INDEX);
			carouselElementRef?.scrollTo({
				left: carouselItemWidth.current,
				behavior: 'smooth',
			});
			return;
		}

		carouselElementRef?.scrollBy({
			left: carouselItemWidth.current,
			behavior: 'smooth',
		});

		setCurrentIndex((prev) => prev + 1);
	};

	const handleCarouselElementRef = useCallback((node: HTMLDivElement) => {
		setCarouselElementRef(node);
	}, []);

	return {
		handleLeftSlideEvent,
		handleRightSlideEvent,
		handleCarouselElementRef,
		currentIndex,
	};
};

export default useCarousel;
