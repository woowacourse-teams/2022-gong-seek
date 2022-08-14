import axios from 'axios';

import { HOME_URL } from '@/constants/url';
import { CommonArticleType } from '@/types/articleResponse';
import { SearchResultType } from '@/types/searchResponse';

export const getSearchResult = async ({
	target,
	cursorId = '',
}: {
	target: string;
	cursorId: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search?searchText=${target}&cursorId=${cursorId}&pageSize=5`,
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
	};
};

export const getArticleByHashTag = (hashTags: string[]) => {
	const accessToken = localStorage.getItem('accessToken');
	const tags = hashTags.join(',');
	return axios.get<{ articles: CommonArticleType[] }>(`${HOME_URL}/articles=?tags=${tags}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
