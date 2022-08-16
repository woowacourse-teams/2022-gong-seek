import axios from 'axios';

import { HOME_URL } from '@/constants/url';
import { SearchResultType } from '@/types/searchResponse';

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
		const { data } = await axios.get<SearchResultType>(
			`${HOME_URL}/api/articles/searchAuthor?author=${target}&cursorId=${cursorId}&pageSize=5`,
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
	}
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/searchText?text=${target}&cursorId=${cursorId}&pageSize=5`,
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
