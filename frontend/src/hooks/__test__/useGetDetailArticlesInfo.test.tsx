import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import useGetDetailArticle from '@/hooks/useGetDetailArticle';
import useGetDetailComment from '@/hooks/useGetDetailComment';
import { CommentHandler } from '@/mock';
import { ArticleHandler } from '@/mock/article';
import { theme } from '@/styles/Theme';
import { ThemeProvider } from '@emotion/react';
import { renderHook } from '@testing-library/react-hooks';

const server = setupServer(...ArticleHandler, ...CommentHandler);

describe('글들에 대한 상세 정보를 제대로 가져오는지에 대해서 테스트 한다', () => {
	beforeAll(() => server.listen());
	afterEach(() => server.resetHandlers());
	afterAll(() => server.close());

	const queryClient = new QueryClient();
	const wrapper = ({ children }: { children: React.ReactNode }) => (
		<React.StrictMode>
			<ThemeProvider theme={theme}>
				<QueryClientProvider client={queryClient}>
					<RecoilRoot>
						<BrowserRouter>{children}</BrowserRouter>
					</RecoilRoot>
				</QueryClientProvider>
			</ThemeProvider>
		</React.StrictMode>
	);

	test('게시글의 상세 정보에 대해서 조회 할 수 있다', async () => {
		const { result, waitFor } = renderHook(() => useGetDetailArticle('1'), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		if (typeof result.current.data === 'undefined') {
			return;
		}

		const { data } = result.current;
		expect(data.id).toEqual(1);
	});

	test('게시글의 댓글에 대해서 조회할 수 있다', async () => {
		const { result, waitFor } = renderHook(() => useGetDetailComment('1'), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		if (typeof result.current.data?.comments === 'undefined') {
			return;
		}

		const { data } = result.current;

		expect(data.comments.length >= 1).toEqual(true);
	});
});
