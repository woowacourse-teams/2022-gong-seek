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
	const encodedTarget = encodeURIComponent(target);
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search/author?author=${encodedTarget}&cursorId=${cursorId}&size=6`,
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
	const encodedTarget = encodeURIComponent(target);
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search/text?text=${encodedTarget}&cursorId=${cursorId}&size=6`,
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

export const getArticleByHashTag = async ({
	hashTags,
	cursorId = '',
}: {
	hashTags: string;
	cursorId: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');
	const encodedTarget = encodeURIComponent(hashTags);
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search/tags?tagsText=${encodedTarget}&cursorId=${cursorId}&size=6`,
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
		hashTags: hashTags,
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
