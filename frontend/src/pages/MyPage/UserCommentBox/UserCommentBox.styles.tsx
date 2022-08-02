import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;

	width: 90%;
	height: ${({ theme }) => theme.size.SIZE_090};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	background-color: ${({ theme }) => theme.colors.GRAY_100};

	padding: ${({ theme }) => theme.size.SIZE_006};

	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const ContentBox = styled.div`
	overflow: hidden;
	word-break: break-all;
	white-space: wrap;

	padding: ${({ theme }) => theme.size.SIZE_002};
`;

export const CommentTime = styled.div`
	width: 100%;
	text-align: right;

	font-size: ${({ theme }) => theme.size.SIZE_012};
	color: ${({ theme }) => theme.colors.BLACK_400};
`;
