import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import { MyPageHandler } from '@/mock';
import useGetUserArticles from '@/pages/MyPage/hooks/useGetUserArticles';
import useGetUserComments from '@/pages/MyPage/hooks/useGetUserComments';
import useGetUserInfo from '@/pages/MyPage/hooks/useGetUserInfo';
import { theme } from '@/styles/Theme';
import { ThemeProvider } from '@emotion/react';
import { renderHook } from '@testing-library/react-hooks';

const server = setupServer(...MyPageHandler);

describe('마이페이지에 대한 정보를 제대로 조회하는지에 대해서 테스트한다', () => {
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

	test('유저의 정보에 대해서 가져오는 것을 테스트 한다', async () => {
		const { result, waitFor } = renderHook(() => useGetUserInfo(), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		if (typeof result.current.data === 'undefined') {
			return;
		}

		const { data } = result.current;
		expect(data.name).toEqual('샐리');
	});

	test('유저가 작성한 글에 대해서 가져오는 것을 테스트 한다', async () => {
		const { result, waitFor } = renderHook(() => useGetUserArticles(), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		if (typeof result.current.data === 'undefined') {
			return;
		}

		const { data } = result.current;
		expect(data.articles[0].title.length >= 1).toEqual(true);
	});

	test('유저가 작성한 댓글에 대해서 가져오는 것을 테스트 한다', async () => {
		const { result, waitFor } = renderHook(() => useGetUserComments(), { wrapper });
		await waitFor(() => result.current.isSuccess, { interval: 100 });
		if (typeof result.current.data === 'undefined') {
			return;
		}

		const { data } = result.current;
		expect(data.comments[0].content.length >= 1).toEqual(true);
	});
});
