import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import useHeartClick from '@/hooks/queries/article/useHeartClick';
import { LikeHandler } from '@/mock';
import { theme } from '@/styles/Theme';
import { ThemeProvider } from '@emotion/react';
import { renderHook } from '@testing-library/react-hooks';

const server = setupServer(...LikeHandler);

describe('useHeartClick 테스트', () => {
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

	test('좋아요를 클릭할시 좋아요가 하나 증가한다.', async () => {
		const { result, waitFor } = renderHook(
			() => useHeartClick({ prevIsLike: false, prevLikeCount: 0, articleId: '1' }),
			{ wrapper },
		);
		result.current.onLikeButtonClick({
			stopPropagation: () => {
				console.log('이벤트 버블링 방지');
			},
		} as React.MouseEvent<SVGElement, MouseEvent>);

		await waitFor(() => result.current.postIsSuccess, { interval: 100 });

		expect(result.current.isLike).toEqual(true);
		expect(result.current.likeCount).toEqual(1);
	});

	test('좋아요 상태에서 한번 더 클릭할시 좋아요가 취소된다.', async () => {
		const { result, waitFor } = renderHook(
			() => useHeartClick({ prevIsLike: true, prevLikeCount: 1, articleId: '1' }),
			{ wrapper },
		);

		result.current.onUnlikeButtonClick({
			stopPropagation: () => {
				console.log('이벤트 버블링 방지');
			},
		} as React.MouseEvent<SVGElement, MouseEvent>);

		await waitFor(() => result.current.deleteIsSuccess, { interval: 100 });

		expect(result.current.isLike).toEqual(false);
		expect(result.current.likeCount).toEqual(0);
	});
});
