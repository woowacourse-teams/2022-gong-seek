import { useEffect, MutableRefObject } from 'react';

const useIntersectionObserver = ({
	hasNext,
	fetchNextPage,
	target,
}: {
	hasNext: boolean;
	fetchNextPage: any;
	target: MutableRefObject<HTMLDivElement>;
}) => {
	useEffect(() => {
		if (!hasNext) {
			return;
		}

		const observer = new IntersectionObserver((entries) => {
			entries.forEach((entry) => entry.isIntersecting && fetchNextPage());
		});

		const el = target && target.current;
		// ??
		if (el === null) return;

		observer.observe(el);
		return () => {
			observer.unobserve(el);
		};
	}, [hasNext, target.current]);
};

export default useIntersectionObserver;
