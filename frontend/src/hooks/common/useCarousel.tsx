import { useState, useCallback, useEffect, useRef } from 'react';

const useCarousel = () => {
	const [carouselElementRef, setCarouselElementRef] = useState<null | HTMLDivElement>(null);
	const [currentIndex, setCurrentIndex] = useState(0);
	const [isScroll, setIsScroll] = useState(false);
	const timerId = useRef<number | null>();

	const handleScrollEnd = () => {
		setIsScroll(true);
		if (timerId.current !== null) {
			clearTimeout(timerId.current);
		}
		timerId.current = window.setTimeout(function () {
			setIsScroll(false);
		}, 100);
	};

	useEffect(() => {
		if (carouselElementRef) {
			carouselElementRef.addEventListener('scroll', handleScrollEnd);

			carouselElementRef.scrollTo({
				left: 335,
				behavior: 'auto',
			});
		}

		return () => {
			carouselElementRef?.removeEventListener('scroll', handleScrollEnd);
		};
	}, [carouselElementRef]);

	const handleLeftSlideEvent = () => {
		if (currentIndex === 0 || isScroll) {
			return;
		}

		carouselElementRef?.scrollBy({
			left: -335,
			behavior: 'smooth',
		});
		setCurrentIndex((prev) => prev - 1);
	};

	const handleRightSlideEvent = () => {
		if (isScroll) {
			return;
		}

		if (currentIndex === 9) {
			setCurrentIndex(0);
			carouselElementRef?.scrollTo({
				left: 335,
				behavior: 'smooth',
			});
			return;
		}

		carouselElementRef?.scrollBy({
			left: 335,
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
