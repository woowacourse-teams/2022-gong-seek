import { PropsWithStrictChildren } from 'gongseek-types';
import { useEffect, useRef } from 'react';
import { InfiniteQueryObserverResult } from 'react-query';

import { infiniteArticleResponse } from '@/types/articleResponse';
import { InfiniteSearchResultType } from '@/types/searchResponse';

export type ObserverResponseType = infiniteArticleResponse | InfiniteSearchResultType;
interface infiniteScrollObserverProps {
	hasNext: boolean;
	fetchNextPage: () => Promise<InfiniteQueryObserverResult<ObserverResponseType, Error>>;
}

const InfiniteScrollObserver = ({
	children,
	hasNext,
	fetchNextPage,
}: PropsWithStrictChildren<infiniteScrollObserverProps, JSX.Element>) => {
	const endFlag = useRef<HTMLDivElement>(null);

	const intersectionObserver = new IntersectionObserver((entries) => {
		if (entries[0].isIntersecting && hasNext) {
			fetchNextPage();
		}
	});

	useEffect(() => {
		if (!endFlag.current) return;
		if (hasNext) {
			intersectionObserver.observe(endFlag.current);
			return;
		}
		intersectionObserver.unobserve(endFlag.current);

		return () => {
			if (!endFlag.current) return;
			intersectionObserver.unobserve(endFlag.current);
		};
	}, [hasNext, endFlag.current]);

	return (
		<>
			{children}
			<div ref={endFlag} />
		</>
	);
};

export default InfiniteScrollObserver;
