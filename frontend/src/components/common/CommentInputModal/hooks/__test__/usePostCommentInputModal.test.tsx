import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import usePostCommentInputModal from '@/components/common/CommentInputModal/hooks/usePostCommentInputModal';
import { CommentHandler } from '@/mock/comment';
import { renderHook } from '@testing-library/react-hooks';

const server = setupServer(...CommentHandler);

describe('usePostCommentInputModal에 대한 테스트', () => {
	beforeAll(() => server.listen());
	afterEach(() => server.resetHandlers());
	afterAll(() => server.close());
	window.alert = jest.fn();

	test('댓글을 등록할 수 있다', async () => {
		const queryClient = new QueryClient();
		const wrapper = ({ children }: { children: React.ReactNode }) => (
			<React.StrictMode>
				<RecoilRoot>
					<QueryClientProvider client={queryClient}>
						<BrowserRouter>{children}</BrowserRouter>
					</QueryClientProvider>
				</RecoilRoot>
			</React.StrictMode>
		);

		const { result, waitFor } = renderHook(
			() =>
				usePostCommentInputModal(() => {
					console.log('창 닫기');
				}),
			{ wrapper },
		);
		result.current.mutate({ content: '제목', id: '1' });

		await waitFor(() => result.current.isSuccess, { interval: 100 });
		expect(result.current.isSuccess).toEqual(true);
	});
});
