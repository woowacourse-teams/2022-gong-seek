import axios from 'axios';

import { HOME_URL } from '@/constants/url';
import { SearchResultType } from '@/types/searchResponse';

export const getUserSearchResult = async ({
	accessToken,
	target,
	cursorId,
	searchIndex,
}: {
	accessToken: string | null;
	target: string;
	cursorId: string;
	searchIndex: string;
}) => {
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search/author?author=${target}&cursorId=${cursorId}&pageSize=5`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId: String(data.articles[data.articles.length - 1].id),
		target: target,
		searchIndex,
	};
};

export const getArticleSearchResult = async ({
	accessToken,
	target,
	cursorId,
	searchIndex,
}: {
	accessToken: string | null;
	target: string;
	cursorId: string;
	searchIndex: string;
}) => {
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search/text?text=${target}&cursorId=${cursorId}&pageSize=5`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId: String(data.articles[data.articles.length - 1].id),
		target: target,
		searchIndex,
	};
};

export const getSearchResult = async ({
	target,
	searchIndex,
	cursorId = '',
}: {
	target: string;
	searchIndex: string;
	cursorId: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');
	if (searchIndex === '유저') {
		const data = await getUserSearchResult({ accessToken, target, searchIndex, cursorId });
		return data;
	}
	const data = await getArticleSearchResult({ accessToken, target, searchIndex, cursorId });
	return data;
};
