import { useMemo, useRef, useState, useEffect } from 'react';

const useCarousel = () => {
	const [currentIndex, setCurrentIndex] = useState(0);
	const [indexLimit, setIndexLimit] = useState(0);
	const carouselElement = useRef<HTMLDivElement>(null);

	const showPopularSlider = useMemo(
		() => [{ transform: `translateX(calc((-270px) * ${currentIndex}))` }],
		[currentIndex],
	);

	const animationTiming = {
		duration: 300,
		fill: 'forwards',
	} as const;

	useEffect(() => {
		carouselElement.current?.animate(showPopularSlider, animationTiming);
	}, [currentIndex]);

	const initCarousel = (maxArticleLength: number) => {
		setCurrentIndex(0);
		setIndexLimit(maxArticleLength);
	};

	const handleLeftSlideEvent = () => {
		if (currentIndex === 0) {
			// 애니메이션
			return;
		}
		setCurrentIndex(currentIndex - 1);
	};

	const handleRightSlideEvent = () => {
		if (currentIndex === indexLimit - 1) {
			// 애니메이션
			return;
		}
		setCurrentIndex(currentIndex + 1);
	};

	return {
		handleLeftSlideEvent,
		handleRightSlideEvent,
		initCarousel,
		carouselElement,
	};
};

export default useCarousel;
