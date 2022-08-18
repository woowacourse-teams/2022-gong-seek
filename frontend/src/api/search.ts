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
	console.log('2.유저검색 url');
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
	console.log('게시글 검색');
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
		console.log('1. 유저일때', searchIndex, target);
		const data = await getUserSearchResult({ accessToken, target, searchIndex, cursorId });
		return data;
	}
	console.log('게시물', searchIndex);
	const data = await getArticleSearchResult({ accessToken, target, searchIndex, cursorId });
	return data;
};
