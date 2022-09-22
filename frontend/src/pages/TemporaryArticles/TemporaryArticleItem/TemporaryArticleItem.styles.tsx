import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_050};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.BLACK_200};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	padding: ${({ theme }) => theme.size.SIZE_010};
`;

export const Title = styled.h2`
	display: block;
	width: 90%;

	font-size: ${({ theme }) => theme.size.SIZE_016};

	text-overflow: ellipsis;
	overflow: hidden;
	word-break: break-all;
	white-space: nowrap;

	border-bottom: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};

	padding-bottom: ${({ theme }) => theme.size.SIZE_014};
`;

export const CreatedAt = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_010};
	margin-top: ${({ theme }) => theme.size.SIZE_010};
`;
